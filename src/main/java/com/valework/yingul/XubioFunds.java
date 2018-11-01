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
import com.valework.yingul.dao.CommissionDao;
import com.valework.yingul.dao.PersonDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.TransactionDao;
import com.valework.yingul.dao.TransactionDetailDao;
import com.valework.yingul.dao.XubioClientDao;
import com.valework.yingul.dao.XubioRequestDao;
import com.valework.yingul.dao.XubioResponseDao;
import com.valework.yingul.dao.XubioSalesInvoiceDao;
import com.valework.yingul.dao.XubioSendTransactionByMailDao;
import com.valework.yingul.dao.XubioTransaccionProductoItemsDao;
import com.valework.yingul.model.Yng_Account;
import com.valework.yingul.model.Yng_Business;
import com.valework.yingul.model.Yng_Commission;
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_Transaction;
import com.valework.yingul.model.Yng_TransactionDetail;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.Yng_XubioClient;
import com.valework.yingul.model.Yng_XubioSalesInvoice;
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
	TransactionDao transactionDao;
	@Autowired
	XubioSendTransactionByMailDao xubioSendTransactionByMailDao;
	@Autowired
	XubioTransaccionProductoItemsDao xubioTransaccionProductoItemsDao;
	@Autowired 
	CommissionDao commissionDao;
	@Autowired
	XubioSalesInvoiceDao xubioSalesInvoiceDao;
	
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
					xubioClient.setCUIT(business.getDocumentNumber().substring(0, 2)+"-"+business.getDocumentNumber().substring(2, 10)+"-"+business.getDocumentNumber().substring(10, 11));
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
	
	public String postCreateSalesInvoice(Yng_XubioSalesInvoice invoice) throws Exception{
		
		List<Yng_XubioTransaccionProductoItems> items = xubioTransaccionProductoItemsDao.findByXubioSalesInvoice(invoice);
		
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
	    		//"  \"importetotal\": "+invoice.getImportetotal()+",\r\n" + 
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
	    		"  \"transaccionProductoItems\": [\r\n";
	    		int i = 0;
	    		for (Yng_XubioTransaccionProductoItems itemxubio : items) {
	    			i++;
	    			json += "{\r\n" + 
		    		    		//"      \"transaccionCVItemId\": 0,\r\n" + 
		    		    		"      \"precioconivaincluido\": "+itemxubio.getPrecioconivaincluido()+",\r\n" + 
		    		    		//"      \"transaccionId\": 14564644,\r\n" + 
		    		    		"      \"producto\": {\r\n" + 
		    		    		//"        \"ID\": -98,\r\n" + 
		    		    		"        \"codigo\": \""+itemxubio.getCodeProducto()+"\",\r\n" + 
		    		    		"        \"nombre\": \""+itemxubio.getProducto()+"\"\r\n" + 
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
		    		    		"      \"precio\": "+itemxubio.getPrecio()+",\r\n" + 
		    		    		//"      \"iva\": 0.4617,\r\n" + 
		    		    		//"      \"importe\": 2.1983,\r\n" + 
		    		    		"      \"total\": "+itemxubio.getTotal()+",\r\n" + 
		    		    		"      \"procentajeDescuento\": 0,\r\n" + 
		    		    		"      \"montoExtento\": 0\r\n" + 
	    		    		"    }\r\n";
	    			if(i != items.size()){
	    		        json+=",";
	    		    }
				}
	    		json+="  ],\r\n" + 
	    		"  \"transaccionPercepcionItems\": [],\r\n" + 
	    		"  \"transaccionCobranzaItems\": []\r\n" + 
	    		"}";
	    
		// crear el request 
	    Date time = new Date();
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
    			invoice.setTransaccionid(jObject.optLong("transaccionid"));
    			invoice.setNumeroDocumento(jObject.optString("numeroDocumento"));
    			invoice.setImporteImpuestos((double)Math.round((jObject.optDouble("importeImpuestos")) * 100d) / 100d);
    			invoice.setImporteGravado((double)Math.round((jObject.optDouble("importeGravado")) * 100d) / 100d);
    			invoice.setDescripcion(jObject.optString("descripcion"));
    			invoice.setType(jObject.optString("type"));
    			invoice.setCAE(jObject.optString("CAE"));
    			invoice.setCAEFechaVto(jObject.optString("CAEFechaVto"));
    			invoice = xubioSalesInvoiceDao.save(invoice);
    			return "save";
    		}
	    }
        response.close();
	    client.close();
    	
    	return "failGeneral";

	}

	public String sendSalesInvoiceByEmail(Yng_XubioSalesInvoice invoice) throws Exception{
		Yng_XubioSendTransactionByMail transactionByMail = new Yng_XubioSendTransactionByMail();
		
		transactionByMail.setTransaccionId(invoice.getTransaccionid());
		transactionByMail.setDestinatarios(invoice.getXubioClient().getEmail());
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

	public String createSalesInvoice(Yng_Confirm confirm) throws Exception{
		Yng_XubioSalesInvoice invoiceSeller = new Yng_XubioSalesInvoice();
		/********CrearCleinte de xubio**********/
		Yng_XubioClient xubioClient = new Yng_XubioClient();
		try {
			xubioClient = xubioClientDao.findByUser(confirm.getSeller());	
		} catch (Exception e) {
		
		}
		if(xubioClient==null || xubioClient.equals(null)) {
			xubioClient = postCreateClient(confirm.getSeller());
		}
		if(xubioClient==null || xubioClient.equals(null)) {
			return "failClient";
		}
		/*******Fin*************************/
		/***********crear principales datos de la factura al vendedor************/
		invoiceSeller.setXubioClient(xubioClient);
		invoiceSeller.setExternalId(String.valueOf(confirm.getConfirmId()));
		invoiceSeller.setCodeCircuitoContable("DEFAULT");
		invoiceSeller.setCircuitoContable("default");
		invoiceSeller.setTipo(1);
		Date time = new Date();
		DateFormat hourdateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		invoiceSeller.setFecha(hourdateFormat1.format(time));
		invoiceSeller.setFechaVto(hourdateFormat1.format(time));
		invoiceSeller.setCondicionDePago(1);
		invoiceSeller.setDeposito("Depósito Universal");
		invoiceSeller.setCodeDeposito("DEPOSITO_UNIVERSAL");
		invoiceSeller.setMoneda("Pesos Argentinos");
		invoiceSeller.setCodeMoneda("PESOS_ARGENTINOS");
		invoiceSeller.setPuntoVenta("0002");
		invoiceSeller.setCotizacion(1);
		invoiceSeller.setCotizacionListaDePrecio(1);
		invoiceSeller.setPorcentajeComision(0);
		invoiceSeller.setCBUInformada(false);
		invoiceSeller.setFacturaNoExportacion(true);
		invoiceSeller.setYngDescription("Factura de venta emitida al vendedor");
		invoiceSeller.setYngStatus("pending");
		invoiceSeller.setConfirm(confirm);
		invoiceSeller = xubioSalesInvoiceDao.save(invoiceSeller);
		/********************fin********************************/
		/*******crear los item de la factura de acuerdo a la venta**********/
		/********obtenemos el tipo de persona para definir el porcentaje de comision*******/
		List<Yng_Person> personList= personDao.findAll();
		Yng_Person person = new Yng_Person();
		for (Yng_Person yng_Person : personList) {
			if(yng_Person.getYng_User().getUsername().equals(confirm.getBuy().getYng_item().getUser().getUsername())) {
				person = yng_Person;
				System.out.println(person.getName()+" todo bien");
			}
		}
		/***********creamos las dos comisiones ****************/
		Yng_Commission commission= new Yng_Commission();
		Yng_Commission commissionPAYU= new Yng_Commission();
		commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
		switch(confirm.getBuy().getYng_item().getType()) {
		case "Product":
			if(person.isBusiness()) {
				commission =commissionDao.findByToWhoAndWhy("Business", "Product");
			}else {
				commission =commissionDao.findByToWhoAndWhy("Person", "Product");
			}
			break;
		case "Property":
			if(confirm.getBuy().getYng_item().getCondition().equals("Rental")) {
				commission =commissionDao.findByConditionAndWhy("Rental", "Property");
			}else {
				commission =commissionDao.findByToWhoAndWhy("All", "Property");
			}
			break;
		case "Motorized":
			if(confirm.getBuy().getYng_item().getCondition().equals("New")) {
				commission =commissionDao.findByConditionAndWhy("New", "Motorized");
			}else {
				commission =commissionDao.findByConditionAndWhy("All", "All");
			}
			break;
		default:
			commission =commissionDao.findByConditionAndWhy("All", "All");
			break;
		}
		/************fin*******************/
		/****************costo de payu con el 21%*********************/
		double costPayu = ((confirm.getBuy().getCost()*commissionPAYU.getPercentage())/100)+commissionPAYU.getFixedPrice();
		double costPayuIva = (double)Math.round((costPayu+(costPayu*21/100)) * 100d) / 100d;
		/*****************************fin************************/
		/***************costo de la comision yingul***********************/
		double costComission=0;
		if(confirm.getBuy().getShippingCost()==0) {
			costComission=(double)Math.round(((((confirm.getBuy().getCost()-costPayuIva-2)*commission.getPercentage())/100)+commission.getFixedPrice()) * 100d) / 100d;
		}else {
			if(confirm.getBuy().getCost()==confirm.getBuy().getItemCost()) {
				costComission=(double)Math.round(((((confirm.getBuy().getCost()-confirm.getBuy().getShippingCost()-costPayuIva-2)*commission.getPercentage())/100)+commission.getFixedPrice()) * 100d) / 100d;
				
				Yng_XubioTransaccionProductoItems sendYingul = new Yng_XubioTransaccionProductoItems();
				sendYingul.setPrecioconivaincluido(confirm.getBuy().getShippingCost());
				sendYingul.setProducto("Yingul Envios");
				sendYingul.setCodeProducto("YINGUL_ENVIOS");
				sendYingul.setDeposito("Depósito Universal");
				sendYingul.setCodeDeposito("DEPOSITO_UNIVERSAL");
				sendYingul.setCantidad(1);
				sendYingul.setPrecio(confirm.getBuy().getShippingCost());
				sendYingul.setTotal(confirm.getBuy().getShippingCost());
				sendYingul.setProcentajeDescuento(0);
				sendYingul.setMontoExtento(0);
				sendYingul.setXubioSalesInvoice(invoiceSeller);
				sendYingul = xubioTransaccionProductoItemsDao.save(sendYingul);
			}else {
				costComission=(double)Math.round(((((confirm.getBuy().getItemCost()-costPayuIva-2)*commission.getPercentage())/100)+commission.getFixedPrice()) * 100d) / 100d;
				
				Yng_XubioSalesInvoice invoiceBuyer = new Yng_XubioSalesInvoice();
				/********CrearCleinte de xubio**********/
				Yng_XubioClient buyer = new Yng_XubioClient();
				try {
					buyer = xubioClientDao.findByUser(confirm.getBuyer());	
				} catch (Exception e) {
				
				}
				if(buyer==null || buyer.equals(null)) {
					buyer = postCreateClient(confirm.getBuyer());
				}
				if(buyer==null || buyer.equals(null)) {
					return "failClient";
				}
				/*******Fin*************************/
				/***********crear principales datos de la factura al vendedor************/
				invoiceBuyer.setXubioClient(buyer);
				invoiceBuyer.setExternalId(String.valueOf(confirm.getConfirmId())+"-1");
				invoiceBuyer.setCodeCircuitoContable("DEFAULT");
				invoiceBuyer.setCircuitoContable("default");
				invoiceBuyer.setTipo(1);
				invoiceBuyer.setFecha(hourdateFormat1.format(time));
				invoiceBuyer.setFechaVto(hourdateFormat1.format(time));
				invoiceBuyer.setCondicionDePago(1);
				invoiceBuyer.setDeposito("Depósito Universal");
				invoiceBuyer.setCodeDeposito("DEPOSITO_UNIVERSAL");
				invoiceBuyer.setMoneda("Pesos Argentinos");
				invoiceBuyer.setCodeMoneda("PESOS_ARGENTINOS");
				invoiceBuyer.setPuntoVenta("0002");
				invoiceBuyer.setCotizacion(1);
				invoiceBuyer.setCotizacionListaDePrecio(1);
				invoiceBuyer.setPorcentajeComision(0);
				invoiceBuyer.setCBUInformada(false);
				invoiceBuyer.setFacturaNoExportacion(true);
				invoiceBuyer.setYngDescription("Factura de envio emitida al comprador");
				invoiceBuyer.setYngStatus("pending");
				invoiceBuyer.setConfirm(confirm);
				invoiceBuyer = xubioSalesInvoiceDao.save(invoiceBuyer);
				
				Yng_XubioTransaccionProductoItems sendYingul = new Yng_XubioTransaccionProductoItems();
				sendYingul.setPrecioconivaincluido(confirm.getBuy().getShippingCost());
				sendYingul.setProducto("Yingul Envios");
				sendYingul.setCodeProducto("YINGUL_ENVIOS");
				sendYingul.setDeposito("Depósito Universal");
				sendYingul.setCodeDeposito("DEPOSITO_UNIVERSAL");
				sendYingul.setCantidad(1);
				sendYingul.setPrecio(confirm.getBuy().getShippingCost());
				sendYingul.setTotal(confirm.getBuy().getShippingCost());
				sendYingul.setProcentajeDescuento(0);
				sendYingul.setMontoExtento(0);
				sendYingul.setXubioSalesInvoice(invoiceBuyer);
				sendYingul = xubioTransaccionProductoItemsDao.save(sendYingul);
			}
		}

		Yng_XubioTransaccionProductoItems comissionYingul = new Yng_XubioTransaccionProductoItems();
		comissionYingul.setPrecioconivaincluido(costComission);
		comissionYingul.setProducto("Comision Yingul");
		comissionYingul.setCodeProducto("COMISION_YINGUL");
		comissionYingul.setDeposito("Depósito Universal");
		comissionYingul.setCodeDeposito("DEPOSITO_UNIVERSAL");
		comissionYingul.setCantidad(1);
		comissionYingul.setPrecio(costComission);
		comissionYingul.setTotal(costComission);
		comissionYingul.setProcentajeDescuento(0);
		comissionYingul.setMontoExtento(0);
		comissionYingul.setXubioSalesInvoice(invoiceSeller);
		comissionYingul = xubioTransaccionProductoItemsDao.save(comissionYingul);
		
		Yng_XubioTransaccionProductoItems yingulPay = new Yng_XubioTransaccionProductoItems();
		yingulPay.setPrecioconivaincluido(costPayuIva);
		yingulPay.setProducto("Yingul Pay");
		yingulPay.setCodeProducto("YINGUL_PAY");
		yingulPay.setDeposito("Depósito Universal");
		yingulPay.setCodeDeposito("DEPOSITO_UNIVERSAL");
		yingulPay.setCantidad(1);
		yingulPay.setPrecio(costPayuIva);
		yingulPay.setTotal(costPayuIva);
		yingulPay.setProcentajeDescuento(0);
		yingulPay.setMontoExtento(0);
		yingulPay.setXubioSalesInvoice(invoiceSeller);
		yingulPay = xubioTransaccionProductoItemsDao.save(yingulPay);
		
		Yng_XubioTransaccionProductoItems administrativeExpenses = new Yng_XubioTransaccionProductoItems();
		administrativeExpenses.setPrecioconivaincluido(2);
		administrativeExpenses.setProducto("Gastos Administrativos");
		administrativeExpenses.setCodeProducto("GASTOS_ADMINISTRATIVOS");
		administrativeExpenses.setDeposito("Depósito Universal");
		administrativeExpenses.setCodeDeposito("DEPOSITO_UNIVERSAL");
		administrativeExpenses.setCantidad(1);
		administrativeExpenses.setPrecio(2);
		administrativeExpenses.setTotal(2);
		administrativeExpenses.setProcentajeDescuento(0);
		administrativeExpenses.setMontoExtento(0);
		administrativeExpenses.setXubioSalesInvoice(invoiceSeller);
		administrativeExpenses = xubioTransaccionProductoItemsDao.save(administrativeExpenses);

		return "save";
	}

}
