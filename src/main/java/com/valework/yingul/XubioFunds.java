package com.valework.yingul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.XubioRequestDao;
import com.valework.yingul.dao.XubioResponseDao;
import com.valework.yingul.model.Yng_Buy;
import com.valework.yingul.model.Yng_Card;
import com.valework.yingul.model.Yng_Payment;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Request;
import com.valework.yingul.model.Yng_RequestBody;
import com.valework.yingul.model.Yng_Response;
import com.valework.yingul.model.Yng_ResponseBody;
import com.valework.yingul.model.Yng_ResponseHeader;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.Yng_XubioRequest;
import com.valework.yingul.model.Yng_XubioResponse;
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
	public String postCreateClient() throws Exception{
		Yng_Standard api = standardDao.findByKey("XUBIO_api_client");
		//crear el referenceCode y el signature
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(api.getValue());
	    String json = "{\r\n" + 
	    		"  \"CUIT\": \"38.015.219\",\r\n" + 
	    		"  \"categoriaFiscal\": {\r\n" + 
	    		"    \"ID\": 4,\r\n" + 
	    		"    \"codigo\": \"MT\",\r\n" + 
	    		"    \"nombre\": \"Monotributista\"\r\n" + 
	    		"  },\r\n" + 
	    		"  \"codigoPostal\": \"1744\",\r\n" + 
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
	    		"  \"direccion\": \"joaquin v gonzales 10440\",\r\n" + 
	    		"  \"email\": \"davidiren45@gmail.com\",\r\n" + 
	    		"  \"identificacionTributaria\": {\r\n" + 
	    		"    \"ID\": 10,\r\n" + 
	    		"    \"codigo\": \"DNI\",\r\n" + 
	    		"    \"nombre\": \"DNI\"\r\n" + 
	    		"  },\r\n" + 
	    		"  \"nombre\": \"Sergio David Iren\",\r\n" + 
	    		"  \"pais\": {\r\n" + 
	    		"    \"ID\": 1,\r\n" + 
	    		"    \"codigo\": \"ARGENTINA\",\r\n" + 
	    		"    \"nombre\": \"Argentina\"\r\n" + 
	    		"  },\r\n" + 
	    		"  \"razonSocial\": \"Sergio David Iren\",\r\n" + 
	    		"  \"telefono\": \"5491123399622\",\r\n" + 
	    		"  \"usrCode\": \"4157\"\r\n" + 
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
    		return String.valueOf(jObject.optLong("cliente_id"));
	    }
        response.close();
	    client.close();
    	
    	return "failXubio";

	}

}
