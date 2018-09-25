package com.valework.yingul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.valework.yingul.dao.BusinessDao;
import com.valework.yingul.dao.PersonDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.TransactionDao;
import com.valework.yingul.dao.TransactionDetailDao;
import com.valework.yingul.dao.XubioClientDao;
import com.valework.yingul.dao.XubioProofOfPurchaseDao;
import com.valework.yingul.dao.XubioRequestDao;
import com.valework.yingul.dao.XubioResponseDao;
import com.valework.yingul.dao.XubioSendTransactionByMailDao;
import com.valework.yingul.dao.XubioTransaccionProductoItemsDao;
import com.valework.yingul.model.Yng_Business;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_Transaction;
import com.valework.yingul.model.Yng_TransactionDetail;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.Yng_XubioClient;
import com.valework.yingul.model.Yng_XubioProofOfPurchase;
import com.valework.yingul.model.Yng_XubioRequest;
import com.valework.yingul.model.Yng_XubioResponse;
import com.valework.yingul.model.Yng_XubioSendTransactionByMail;
import com.valework.yingul.model.Yng_XubioTransaccionProductoItems;
import com.valework.yingul.util.VisaAPIClient;

@Component
public class XubioFunds {
	final static Logger logger = Logger.getLogger(VisaAPIClient.class);
	@Autowired 
	StandardDao standardDao;
	@Autowired 
	XubioRequestDao xubioRequestDao;
	@Autowired
	XubioResponseDao xubioResponseDao;
	@Autowired
	PersonDao personDao;
	@Autowired
	BusinessDao businessDao;
	@Autowired
	XubioClientDao xubioClientDao;
	@Autowired
	TransactionDetailDao transactionDetailDao;
	@Autowired
	XubioProofOfPurchaseDao xubioProofOfPurchaseDao;
	@Autowired 
	TransactionDao transactionDao;
	@Autowired
	XubioSendTransactionByMailDao xubioSendTransactionByMailDao;
	@Autowired
	XubioTransaccionProductoItemsDao xubioTransaccionProductoItemsDao;
	
	public Yng_Person getPersonForUser(Yng_User user) {
		List<Yng_Person> personList= personDao.findAll();
		Yng_Person person = new Yng_Person();
		for (Yng_Person yng_Person : personList) {
			if(yng_Person.getYng_User().getUsername().equals(user.getUsername())) {
				person = yng_Person;
				return person;
			}
		}
		return null;
	}
	public Yng_XubioResponse logResponse(CloseableHttpResponse response) throws IOException, JSONException {
		Yng_XubioResponse responseTemp= new Yng_XubioResponse();
    	Header[] h = response.getAllHeaders();
        
        // Get the response json object
    	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        
        // Print the response details
        HttpEntity entity = response.getEntity();
        logger.info("Response status : " + response.getStatusLine() + "\n");
        responseTemp.setStatus(response.getStatusLine().toString());
        logger.info("Response Headers: \n");
        String header="";
        for (int i = 0; i < h.length; i++) {
            logger.info(h[i].getName() + ":" + h[i].getValue());
            header+= h[i].getName() + ":" + h[i].getValue()+",";
        }
        responseTemp.setHeader(header);
        logger.info("\n Response Body:");
        
        if(!StringUtils.isEmpty(result.toString())) {
            ObjectMapper mapper = getObjectMapperInstance();
            Object tree;
            try {
                tree = mapper.readValue(result.toString(), Object.class);
                logger.info("ResponseBody: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree));
                String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree);
                responseTemp.setBody(s);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
                responseTemp.setMessage(e.getMessage().toString());
            } catch (IOException e) {
                logger.error(e.getMessage());
                responseTemp.setMessage(e.getMessage().toString());
            }
        }
        
        EntityUtils.consume(entity);
        return responseTemp;
    }
	protected static ObjectMapper getObjectMapperInstance() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true); // format json
        return mapper;
    }
	
	public String getToken() throws Exception{
		
		Yng_Standard api = standardDao.findByKey("XUBIO_api_get_token");
		//crear el referenceCode y el signature
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(api.getValue());
	    String json = "{\"grant_type\":\"client_credentials\"}";
		// crear el request 
	    Date time = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String date=hourdateFormat.format(time);
		Yng_XubioRequest requestTemp = new Yng_XubioRequest(); 
		requestTemp.setURI(api.getValue());
		requestTemp.setInfo("Get Token");
		requestTemp.setBody(json);
		requestTemp.setDate(date);
		requestTemp = xubioRequestDao.save(requestTemp);
	    List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	    nvps.add(new BasicNameValuePair("grant_type", "client_credentials"));
	    httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	    httpPost.setHeader("Authorization", "Basic MDc0NzgyMDUxNjkwNjM3ODk3Nzc2MzczNTUxMzY3MTM4MjAwMTg4MDI3MTY0NDA4OTg1MzM0MzcxOTYwOTQzMTI4NjU1ODYzNTYxODkyMDUxNjA3NDc4ODU5MjM1MDQwNzpqR0E1KklCaTNEQ21mYkZ6U2FZZEEzb1VfdT03LXF4WDQ2YWJzSVhnKkFBRnVrcWpiQUxhRm9PUC9GaUhjWmtSZ3ZRUnluREdOR0R2REdybVZQa2ZwMy1GYXh3YVptVDYxKnVlMXdqR0E1KklCaTNEQ21mYkZ6U2FZZEEzb1VfdT0=");
	    //httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	    CloseableHttpResponse response = client.execute(httpPost);
	    Yng_XubioResponse responseTemp= this.logResponse(response);
	   
    	responseTemp=xubioResponseDao.save(responseTemp);
    	requestTemp.setXubioResponse(responseTemp);
    	requestTemp=xubioRequestDao.save(requestTemp);
    	if(responseTemp.getStatus().equals("HTTP/1.1 200 OK")) {
    		JSONObject  jObject = new JSONObject(responseTemp.getBody());
    		return jObject.optString("access_token");
	    }
        response.close();
	    client.close();
    	
    	return "failXubio";

	}
	public Yng_XubioClient postCreateClient(Yng_User user) throws Exception{
		Yng_XubioClient xubioClient = new Yng_XubioClient();
		Yng_Person person = getPersonForUser(user);
		if(person.isBusiness()) {
			Yng_Business business = businessDao.findByUser(user);
			xubioClient.setNombre(person.getName()+" "+person.getLastname());
			xubioClient.setRazonSocial(business.getBusinessName());
			switch (business.getContributorType()) {
				case "Consumidor Final":  
					xubioClient.setCodeCategoriaFiscal("CF");
		        	break;
				case "Exento":  
					xubioClient.setCodeCategoriaFiscal("EX");
		        	break;
				case "Exterior":  
					//xubioClient.setCodeCategoriaFiscal("E");
		        	break;
				case "IVA No Alcanzado":  
					xubioClient.setCodeCategoriaFiscal("NA");
		        	break;
				case "Monotributista":  
					xubioClient.setCodeCategoriaFiscal("MT");
		        	break;
				case "Responsable Inscripto":  
					xubioClient.setCodeCategoriaFiscal("RI");
		        	break;
			}
			xubioClient.setCategoriaFiscal(business.getContributorType());
			switch (business.getDocumentType()) {
		        case "DNI":  
		        	xubioClient.setCodeIdentificacionTributaria("DNI");
					xubioClient.setIdentificacionTributaria("DNI");
					xubioClient.setCUIT(business.getDocumentNumber().substring(0, 2)+"."+business.getDocumentNumber().substring(2, 5)+"."+business.getDocumentNumber().substring(5, 8));
		        	break;
		        case "CUIT":  
		        	xubioClient.setCodeIdentificacionTributaria("CUIT");
					xubioClient.setIdentificacionTributaria("CUIT");
					xubioClient.setCUIT(business.getDocumentNumber().substring(0, 2)+"."+business.getDocumentNumber().substring(2, 5)+"."+business.getDocumentNumber().substring(5, 8));
		        	break;
	        }
		}else {
			xubioClient.setNombre(person.getName()+" "+person.getLastname());
			xubioClient.setRazonSocial(person.getName()+" "+person.getLastname());
			xubioClient.setCodeCategoriaFiscal("CF");
			xubioClient.setCategoriaFiscal("Consumidor Final");
			switch (user.getDocumentType()) {
		        case "DNI":  
		        	xubioClient.setCodeIdentificacionTributaria("DNI");
					xubioClient.setIdentificacionTributaria("DNI");
					xubioClient.setCUIT(user.getDocumentNumber().substring(0, 2)+"."+user.getDocumentNumber().substring(2, 5)+"."+user.getDocumentNumber().substring(5, 8));
		        	break;
		        case "CUIT":  
		        	xubioClient.setCodeIdentificacionTributaria("CUIT");
					xubioClient.setIdentificacionTributaria("CUIT");
					xubioClient.setCUIT(user.getDocumentNumber().substring(0, 2)+"-"+user.getDocumentNumber().substring(2, 10)+"-"+user.getDocumentNumber().substring(10, 11));
		        	break;
	        }
		}	
			
		Yng_Standard api = standardDao.findByKey("XUBIO_api_client");
		//crear el referenceCode y el signature
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(api.getValue());
	    String json = "{\r\n" + 
	    		"  \"CUIT\": \""+xubioClient.getCUIT()+"\",\r\n" + 
	    		"  \"categoriaFiscal\": {\r\n" + 
	    		"    \"codigo\": \""+xubioClient.getCodeCategoriaFiscal()+"\",\r\n" + 
	    		"    \"nombre\": \""+xubioClient.getCategoriaFiscal()+"\"\r\n" + 
	    		"  },\r\n" + 
	    		"  \"codigoPostal\": \""+user.getYng_Ubication().getPostalCode()+"\",\r\n" + 
	    		"  \"cuentaCompra_id\": {\r\n" + 
	    		"    \"ID\": -7,\r\n" + 
	    		"    \"codigo\": \"PROVEEDORES\",\r\n" + 
	    		"    \"nombre\": \"Proveedores\"\r\n" + 
	    		"  },\r\n" + 
	    		"  \"cuentaVenta_id\": {\r\n" + 
	    		"    \"ID\": -3,\r\n" + 
	    		"    \"codigo\": \"DEUDORES_POR_VENTA\",\r\n" + 
	    		"    \"nombre\": \"Deudores por Venta\"\r\n" + 
	    		"  },\r\n" + 
	    		"  \"descripcion\": \"\",\r\n" + 
	    		"  \"direccion\": \""+user.getYng_Ubication().getStreet()+" "+user.getYng_Ubication().getNumber()+"\",\r\n" + 
	    		"  \"email\": \""+user.getEmail()+"\",\r\n" + 
	    		"  \"identificacionTributaria\": {\r\n" + 
	    		"    \"codigo\": \""+xubioClient.getCodeIdentificacionTributaria()+"\",\r\n" + 
	    		"    \"nombre\": \""+xubioClient.getIdentificacionTributaria()+"\"\r\n" + 
	    		"  },\r\n" + 
	    		"  \"nombre\": \""+xubioClient.getNombre()+"\",\r\n" + 
	    		"  \"pais\": {\r\n" + 
	    		"    \"ID\": 1,\r\n" + 
	    		"    \"codigo\": \"ARGENTINA\",\r\n" + 
	    		"    \"nombre\": \"Argentina\"\r\n" + 
	    		"  },\r\n" + 
	    		"  \"razonSocial\": \""+xubioClient.getRazonSocial()+"\",\r\n" + 
	    		"  \"telefono\": \""+user.getPhone()+"\",\r\n" + 
	    		"  \"usrCode\": \""+user.getUserId()+"\"\r\n" + 
	    		"}";
	    
		// crear el request 
	    Date time = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String date=hourdateFormat.format(time);
		Yng_XubioRequest requestTemp = new Yng_XubioRequest(); 
		requestTemp.setURI(api.getValue());
		requestTemp.setInfo("Create client");
		requestTemp.setBody(json);
		requestTemp.setDate(date);
		requestTemp = xubioRequestDao.save(requestTemp);
	    
	    StringEntity entity = new StringEntity(json, "UTF-8");
	    httpPost.setEntity(entity);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-type", "application/json; charset=utf-8");
	    String authorization = getToken();
	    if(authorization.equals("failXubio")) {
	    	return null;
	    }
	    httpPost.setHeader("Authorization", "Bearer "+authorization);
	    
	    CloseableHttpResponse response = client.execute(httpPost);
	    Yng_XubioResponse responseTemp= this.logResponse(response);
	   
    	responseTemp=xubioResponseDao.save(responseTemp);
    	requestTemp.setXubioResponse(responseTemp);
    	requestTemp=xubioRequestDao.save(requestTemp);
    	if(responseTemp.getStatus().equals("HTTP/1.1 200 OK")) {
    		JSONObject  jObject = new JSONObject(responseTemp.getBody());
    		if(jObject.has("cliente_id")) {
    			xubioClient.setCUIT(jObject.optString("CUIT"));
    			xubioClient.setCliente_id(jObject.optLong("cliente_id"));
    			xubioClient.setCodigoPostal(jObject.optString("codigoPostal"));
    			xubioClient.setDireccion(jObject.optString("direccion"));
    			xubioClient.setEmail(jObject.optString("email"));
    			xubioClient.setTelefono(jObject.optString("telefono"));
    			xubioClient.setUsrCode(jObject.optString("usrCode"));
    			xubioClient.setUser(user);
    			xubioClient=xubioClientDao.save(xubioClient);
    			return xubioClient;
    		}
	    }
        response.close();
	    client.close();
    	
    	return null;

	}
	
	public String postCreateInvoice(Yng_Transaction transaction) throws Exception{
		Yng_XubioProofOfPurchase invoice = new Yng_XubioProofOfPurchase();
		
		Yng_XubioClient xubioClient = new Yng_XubioClient();
		try {
			xubioClient = xubioClientDao.findByUser(transaction.getAccount().getUser());	
		} catch (Exception e) {
		
		}
		if(xubioClient==null || xubioClient.equals(null)) {
			xubioClient = postCreateClient(transaction.getAccount().getUser());
		}
		if(xubioClient==null || xubioClient.equals(null)) {
			return "failClient";
		}
		invoice.setXubioClient(xubioClient);
		switch(transaction.getTypeCode()) {
			case "CTV":
				Yng_TransactionDetail transactionDetail = transactionDetailDao.findByTransaction(transaction);
				invoice.setImportetotal(transactionDetail.getCostCommission());
				break;
		}
		invoice.setCodeCircuitoContable("DEFAULT");
		invoice.setCircuitoContable("default");
		invoice.setExternalId(String.valueOf(transaction.getTransactionId()));
		invoice.setTipo(1);
		Date time = new Date();
		DateFormat hourdateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		invoice.setFecha(hourdateFormat1.format(time));
		invoice.setFechaVto(hourdateFormat1.format(time));
		invoice.setCondicionDePago(1);
		invoice.setDeposito("Depósito Universal");
		invoice.setCodeDeposito("DEPOSITO_UNIVERSAL");
		//invoice.setPuntoVenta("");
		//invoice.setCodePuntoVenta("");
		invoice.setCotizacion(1);
		invoice.setCotizacionListaDePrecio(1);
		invoice.setPorcentajeComision(0);
		invoice.setCBUInformada(false);
		invoice.setFacturaNoExportacion(true);
		
		Yng_XubioTransaccionProductoItems productoItems = new Yng_XubioTransaccionProductoItems();
		productoItems.setPrecioconivaincluido(invoice.getImportetotal());
		productoItems.setProducto("Servicio al 21%");
		productoItems.setCodeProducto("SERVICIO_AL_21");
		productoItems.setDeposito("Depósito Universal");
		productoItems.setCodeDeposito("DEPOSITO_UNIVERSAL");
		productoItems.setCantidad(1);
		productoItems.setPrecio(invoice.getImportetotal());
		productoItems.setTotal(productoItems.getPrecio());
		productoItems.setProcentajeDescuento(0);
		productoItems.setMontoExtento(0);
		
		Yng_Standard api = standardDao.findByKey("XUBIO_api_proof_of_purchase");
		//crear el referenceCode y el signature
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(api.getValue());
	    String json = "{\r\n" + 
	    		"  \"circuitoContable\": {\r\n" + 
	    		"    \"ID\": -2,\r\n" + 
	    		"    \"codigo\": \"DEFAULT\",\r\n" + 
	    		"    \"nombre\": \"default\"\r\n" + 
	    		"  },\r\n" + 
	    		//"  \"transaccionid\": 14564644,\r\n" + 
	    		"  \"externalId\": \""+invoice.getExternalId()+"\",\r\n" + 
	    		"  \"cliente\": {\r\n" + 
	    		"    \"ID\": "+invoice.getXubioClient().getCliente_id()+"\r\n" + 
	    		//"    \"codigo\": \"FREDDY_IVAN_QUISPE_CONDORI\",\r\n" + 
	    		//"    \"nombre\": \"Freddy Ivan Quispe Condori\"\r\n" + 
	    		"  },\r\n" + 
	    		"  \"tipo\": 1,\r\n" + 
	    		"  \"nombre\": \"\",\r\n" + 
	    		"  \"fecha\": \""+invoice.getFecha()+"\",\r\n" + 
	    		"  \"fechaVto\": \""+invoice.getFechaVto()+"\",\r\n" + 
	    		"  \"puntoVenta\": {\r\n" + 
	    		//"    \"ID\": 113819,\r\n" + preguntar a ivan si el punto de venta va directamente aqui
	    		//"    \"codigo\": \"0001\",\r\n" + 
	    		"    \"nombre\": \"0002\"\r\n" + 
	    		"  },\r\n" + 
	    		//"  \"numeroDocumento\": \"B-0001-00000001\",\r\n" + 
	    		"  \"condicionDePago\": 1,\r\n" + 
	    		"  \"deposito\": {\r\n" + 
	    		"    \"ID\": -2,\r\n" + 
	    		"    \"codigo\": \"DEPOSITO_UNIVERSAL\",\r\n" + 
	    		"    \"nombre\": \"Depósito Universal\"\r\n" + 
	    		"  },\r\n" + 
	    		//"  \"primerTktA\": \"string\",\r\n" + 
	    		//"  \"ultimoTktA\": \"string\",\r\n" + 
	    		//"  \"primerTktBC\": \"string\",\r\n" + 
	    		//"  \"ultimoTktBC\": \"string\",\r\n" + 
	    		//"  \"cantComprobantesEmitidos\": 0,\r\n" + 
	    		//"  \"cantComprobantesCancelados\": 0,\r\n" + 
	    		"  \"cotizacion\": 1,\r\n" + 
	    		"  \"moneda\": {\r\n" + 
	    		"    \"ID\": -2,\r\n" + 
	    		"    \"codigo\": \"PESOS_ARGENTINOS\",\r\n" + 
	    		"    \"nombre\": \"Pesos Argentinos\"\r\n" + 
	    		"  },\r\n" + 
	    		//"  \"importeMonPrincipal\": 0,\r\n" + 
	    		"  \"importetotal\": "+invoice.getImportetotal()+",\r\n" + 
	    		//"  \"importeImpuestos\": 0.46,\r\n" + 
	    		//"  \"importeGravado\": 2.2,\r\n" + 
	    		"  \"provincia\": {\r\n" + 
	    		"    \"ID\": 1,\r\n" + 
	    		"    \"codigo\": \"BUENOS_AIRES\",\r\n" + 
	    		"    \"nombre\": \"Buenos Aires\"\r\n" + 
	    		"  },\r\n" + 
	    		"  \"cotizacionListaDePrecio\": 1,\r\n" + 
	    		//"  \"listaDePrecio\": {\r\n" + 
	    		//"    \"nombre\": \"string\",\r\n" + 
	    		//"    \"codigo\": \"string\",\r\n" + 
	    		//"    \"id\": 0\r\n" + 
	    		//"  },\r\n" + 
	    		//"  \"vendedor\": {\r\n" + 
	    		//"    \"nombre\": \"string\",\r\n" + 
	    		//"    \"codigo\": \"string\",\r\n" + 
	    		//"    \"id\": 0\r\n" + 
	    		//"  },\r\n" + 
	    		"  \"porcentajeComision\": 0,\r\n" + 
	    		//"  \"mailEstado\": \"string\",\r\n" + 
	    		"  \"descripcion\": \"\",\r\n" + 
	    		"  \"CBUInformada\": false,\r\n" + 
	    		"  \"facturaNoExportacion\": true,\r\n" + 
	    		"  \"transaccionProductoItems\": [\r\n" + 
	    		"    {\r\n" + 
	    		//"      \"transaccionCVItemId\": 0,\r\n" + 
	    		"      \"precioconivaincluido\": "+productoItems.getPrecioconivaincluido()+",\r\n" + 
	    		//"      \"transaccionId\": 14564644,\r\n" + 
	    		"      \"producto\": {\r\n" + 
	    		"        \"ID\": -98,\r\n" + 
	    		"        \"codigo\": \"SERVICIO_AL_21\",\r\n" + 
	    		"        \"nombre\": \"Servicio al 21%\"\r\n" + 
	    		"      },\r\n" + 
	    		//"      \"centroDeCosto\": {\r\n" + 
	    		//"        \"nombre\": \"string\",\r\n" + 
	    		//"        \"codigo\": \"string\",\r\n" + 
	    		//"        \"id\": 0\r\n" + 
	    		//"      },\r\n" + 
	    		"      \"deposito\": {\r\n" + 
	    		"        \"ID\": -2,\r\n" + 
	    		"        \"codigo\": \"DEPOSITO_UNIVERSAL\",\r\n" + 
	    		"        \"nombre\": \"Depósito Universal\"\r\n" + 
	    		"      },\r\n" + 
	    		"      \"descripcion\": \"\",\r\n" + 
	    		"      \"cantidad\": 1,\r\n" + 
	    		"      \"precio\": "+productoItems.getPrecio()+",\r\n" + 
	    		//"      \"iva\": 0.4617,\r\n" + 
	    		//"      \"importe\": 2.1983,\r\n" + 
	    		"      \"total\": "+productoItems.getTotal()+",\r\n" + 
	    		"      \"procentajeDescuento\": 0,\r\n" + 
	    		"      \"montoExtento\": 0\r\n" + 
	    		"    }\r\n" + 
	    		"  ],\r\n" + 
	    		"  \"transaccionPercepcionItems\": [],\r\n" + 
	    		"  \"transaccionCobranzaItems\": []\r\n" + 
	    		"}";
	    
		// crear el request 
    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String date=hourdateFormat.format(time);
		Yng_XubioRequest requestTemp = new Yng_XubioRequest(); 
		requestTemp.setURI(api.getValue());
		requestTemp.setInfo("Create invoice");
		requestTemp.setBody(json);
		requestTemp.setDate(date);
		requestTemp = xubioRequestDao.save(requestTemp);
	    
	    StringEntity entity = new StringEntity(json, "UTF-8");
	    httpPost.setEntity(entity);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-type", "application/json; charset=utf-8");
	    String authorization = getToken();
	    if(authorization.equals("failXubio")) {
	    	return "failAuthorization";
	    }
	    httpPost.setHeader("Authorization", "Bearer "+authorization);
	    
	    CloseableHttpResponse response = client.execute(httpPost);
	    Yng_XubioResponse responseTemp= this.logResponse(response);
	   
    	responseTemp=xubioResponseDao.save(responseTemp);
    	requestTemp.setXubioResponse(responseTemp);
    	requestTemp=xubioRequestDao.save(requestTemp);
    	if(responseTemp.getStatus().equals("HTTP/1.1 200 OK")) {
    		JSONObject  jObject = new JSONObject(responseTemp.getBody());
    		if(jObject.has("transaccionid")) {
    			productoItems.setTransaccionId(jObject.optJSONArray("transaccionProductoItems").optJSONObject(0).optLong("transaccionId"));
    			productoItems.setIva((double)Math.round((jObject.optJSONArray("transaccionProductoItems").optJSONObject(0).optDouble("iva")) * 100d) / 100d);
    			productoItems.setImporte((double)Math.round((jObject.optJSONArray("transaccionProductoItems").optJSONObject(0).optDouble("importe")) * 100d) / 100d);
    			productoItems = xubioTransaccionProductoItemsDao.save(productoItems);
    			invoice.setTransaccionid(jObject.optLong("transaccionid"));
    			invoice.setNumeroDocumento(jObject.optString("numeroDocumento"));
    			invoice.setImporteImpuestos((double)Math.round((jObject.optDouble("importeImpuestos")) * 100d) / 100d);
    			invoice.setImporteGravado((double)Math.round((jObject.optDouble("importeGravado")) * 100d) / 100d);
    			invoice.setDescripcion(jObject.optString("descripcion"));
    			invoice.setType(jObject.optString("type"));
    			invoice.setCAE(jObject.optString("CAE"));
    			invoice.setCAEFechaVto(jObject.optString("CAEFechaVto"));
    			invoice.setXubioTransaccionProductoItems(productoItems);
    			invoice.setTransaction(transaction);
    			invoice = xubioProofOfPurchaseDao.save(invoice);
    			return "save";
    		}
	    }
        response.close();
	    client.close();
    	
    	return "failGeneral";

	}

	public String sendInvoiceByEmail(Yng_Transaction transaction) throws Exception{
		Yng_XubioSendTransactionByMail transactionByMail = new Yng_XubioSendTransactionByMail();
		
		Yng_XubioProofOfPurchase invoice = new Yng_XubioProofOfPurchase();	
		invoice = xubioProofOfPurchaseDao.findByTransaction(transaction);
		
		transactionByMail.setTransaccionId(invoice.getTransaccionid());
		transactionByMail.setDestinatarios(transaction.getAccount().getUser().getEmail());
		transactionByMail.setCopiaCon("noreply@internetvale.com");
		transactionByMail.setCopiaConOtro("quenallataeddy@gmail.com");//solo para las pruebas 
		transactionByMail.setAsunto("Factura correspondiente a los servicios de Yingul Company SRL");
		transactionByMail.setCuerpo("Estimado cliente, La factura adjunta es de Carácter informativo, los valores ya fueron debitados de su cuenta virtual en Yingul Pay Importante: No debe realizar ningun pago a Yingul Pay por esta factura. Cordial Saludo.");
			
		Yng_Standard api = standardDao.findByKey("XUBIO_api_send_by_mail");
		//crear el referenceCode y el signature
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(api.getValue());
	    String json = "{\r\n" + 
		    		"  \"transaccionId\": "+transactionByMail.getTransaccionId()+",\r\n" + 
		    		"  \"destinatarios\": \""+transactionByMail.getDestinatarios()+"\",\r\n" + 
		    		"  \"copiaCon\": \""+transactionByMail.getCopiaCon()+"\",\r\n" + 
		    		"  \"copiaConOtro\": \""+transactionByMail.getCopiaConOtro()+"\",\r\n" + 
		    		"  \"asunto\": \""+transactionByMail.getAsunto()+"\",\r\n" + 
		    		"  \"cuerpo\": \""+transactionByMail.getCuerpo()+"\"\r\n" + 
	    		"}";
	    
		// crear el request 
	    Date time = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String date=hourdateFormat.format(time);
		Yng_XubioRequest requestTemp = new Yng_XubioRequest(); 
		requestTemp.setURI(api.getValue());
		requestTemp.setInfo("Send transaction by email");
		requestTemp.setBody(json);
		requestTemp.setDate(date);
		requestTemp = xubioRequestDao.save(requestTemp);
	    
	    StringEntity entity = new StringEntity(json, "UTF-8");
	    httpPost.setEntity(entity);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-type", "application/json; charset=utf-8");
	    String authorization = getToken();
	    if(authorization.equals("failXubio")) {
	    	return "failXubio";
	    }
	    httpPost.setHeader("Authorization", "Bearer "+authorization);
	    
	    CloseableHttpResponse response = client.execute(httpPost);
	    Yng_XubioResponse responseTemp= this.logResponse(response);
	   
    	responseTemp=xubioResponseDao.save(responseTemp);
    	requestTemp.setXubioResponse(responseTemp);
    	requestTemp=xubioRequestDao.save(requestTemp);
    	if(responseTemp.getStatus().equals("HTTP/1.1 200 OK")) {
    		JSONObject  jObject = new JSONObject(responseTemp.getBody());
    		if(jObject.has("transaccionId")) {
    			transactionByMail = xubioSendTransactionByMailDao.save(transactionByMail);
    			return "save";
    		}
	    }
        response.close();
	    client.close();
    	
    	return "failXubio";

	}

}
