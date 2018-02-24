package com.valework.yingul.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.Header;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.logistic.AccessTokenDHL;
import com.valework.yingul.logistic.DhlResponse;
import com.valework.yingul.logistic.DhlShipments;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_Token;
import com.valework.yingul.service.StandardService;

@RestController
@RequestMapping("/send")
public class LogisticDHL {
	@RequestMapping("/logistics")
	public String dhlEnvio() {
    	HttpClient httpClient = new DefaultHttpClient();

    	try {
    	    HttpPost request = new HttpPost("https://private-anon-e5589704da-dhlgloballabel.apiary-proxy.com/shipping/v1/label?format=PNG&labelSize=4x6");
    	    StringEntity params =new StringEntity("{\r\n" + 
    	    		"  \"shipments\": [\r\n" + 
    	    		"    {\r\n" + 
    	    		"      \"packages\": [\r\n" + 
    	    		"        {\r\n" + 
    	    		"          \"packageDetails\": {\r\n" + 
    	    		"            \"packageRefName\": \"55555555555555555\",\r\n" + 
    	    		"            \"insuredValue\": 1,\r\n" + 
    	    		"            \"weight\": 2,\r\n" + 
    	    		"            \"declaredValue\": 250,\r\n" + 
    	    		"            \"mailType\": 2,\r\n" + 
    	    		"            \"height\": 30,\r\n" + 
    	    		"            \"packageDesc\": \"Desc111\",\r\n" + 
    	    		"            \"currency\": \"AUD\",\r\n" + 
    	    		"            \"length\": 20,\r\n" + 
    	    		"            \"weightUom\": \"G\",\r\n" + 
    	    		"            \"billingRef2\": \"ref 2\",\r\n" + 
    	    		"            \"packageId\": \"fybjywwbdt\",\r\n" + 
    	    		"            \"dutiesPaid\": \"DDU\",\r\n" + 
    	    		"            \"orderedProduct\": \"PPS\",\r\n" + 
    	    		"            \"dimensionUom\": \"CM\",\r\n" + 
    	    		"            \"billingRef1\": \"ref 1\"\r\n" + 
    	    		"          },\r\n" + 
    	    		"          \"consigneeAddress\": {\r\n" + 
    	    		"            \"city\": \"Test City\",\r\n" + 
    	    		"            \"name\": \"Test Name\",\r\n" + 
    	    		"            \"address1\": \"Address line 1\",\r\n" + 
    	    		"            \"address2\": \"apt 123\",\r\n" + 
    	    		"            \"phone\": \"555-555-5555\",\r\n" + 
    	    		"            \"state\": \"GA\",\r\n" + 
    	    		"            \"country\": \"IE\",\r\n" + 
    	    		"            \"postalCode\": \"99999\",\r\n" + 
    	    		"            \"email\": \"test@email.com\"\r\n" + 
    	    		"          },\r\n" + 
    	    		"          \"customsDetails\": [\r\n" + 
    	    		"            {\r\n" + 
    	    		"              \"skuNumber\": \"3333333333333\",\r\n" + 
    	    		"              \"countryOfOrigin\": \"US\",\r\n" + 
    	    		"              \"itemDescription\": \"Desc1\",\r\n" + 
    	    		"              \"itemValue\": 10.1,\r\n" + 
    	    		"              \"packagedQuantity\": 10,\r\n" + 
    	    		"              \"hsCode\": \"555555\"\r\n" + 
    	    		"            },\r\n" + 
    	    		"            {\r\n" + 
    	    		"              \"skuNumber\": \"3333333333333\",\r\n" + 
    	    		"              \"countryOfOrigin\": \"CZ\",\r\n" + 
    	    		"              \"itemDescription\": \"Desc2\",\r\n" + 
    	    		"              \"itemValue\": 20.2,\r\n" + 
    	    		"              \"packagedQuantity\": 20,\r\n" + 
    	    		"              \"hsCode\": \"555555\"\r\n" + 
    	    		"            }\r\n" + 
    	    		"          ],\r\n" + 
    	    		"          \"returnAddress\": {\r\n" + 
    	    		"            \"city\": \"Test City\",\r\n" + 
    	    		"            \"name\": \"John Returns Doe\",\r\n" + 
    	    		"            \"companyName\": \"Test Company\",\r\n" + 
    	    		"            \"country\": \"US\",\r\n" + 
    	    		"            \"state\": \"GA\",\r\n" + 
    	    		"            \"address1\": \"Address line 1\",\r\n" + 
    	    		"            \"postalCode\": \"99999\"\r\n" + 
    	    		"          }\r\n" + 
    	    		"        }\r\n" + 
    	    		"      ],\r\n" + 
    	    		"      \"pickupAccount\": \"5317861\",\r\n" + 
    	    		"      \"distributionCenter\": \"HKHKG1\"\r\n" + 
    	    		"    }\r\n" + 
    	    		"  ]\r\n" + 
    	    		"}");
    	    request.addHeader("Content-Type","application/json");
    	    request.addHeader("Accept", "application/json");
    	    request.addHeader("Authorization", "Bearer 877DjhTWgvalgJX0VD7FrPkvvoelq053ANS5GLxF6ho0Pnknld3KyF");
    	    request.setEntity(params);
    	    HttpResponse response = httpClient.execute(request);
    	    System.out.println("response code:"+response.getStatusLine().getStatusCode() + "");
    	    List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());        
    	    for (Header header : httpHeaders) {
    	        System.out.println("Headers.. name,value:"+header.getName() + "," + header.getValue());
    	    }
    	    
    	    String result = EntityUtils.toString(response.getEntity());
    	    System.out.println("response body : "+result + "");
    	    jsonShipments(result);
    	    
    	    
    	    
    	}catch (Exception ex) {
    	    // handle exception here
    	} finally {
    	    httpClient.getConnectionManager().shutdown();
    	}
    	return "save";
    }
    private final String USER_AGENT = "Mozilla/5.0";


	@RequestMapping("/logistics4")
	public String da4() throws IOException {
		String urlDomiciliio="https://private-anon-ee29acebcf-efulfillment1.apiary-proxy.com/efulfillment/v1/auth/accesstoken";

 		
 		URL obj = new URL(urlDomiciliio);
 		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

 		// optional default is GET
 		con.setRequestMethod("GET");
 		con.setRequestProperty("Accept", "application/json");
 		con.setRequestProperty("Authorization", "Basic ZTllZDgyYTgtNDIzNy00MTg1LThlMzYtNDcyNjRhYTllNzE4OmIxZWQxYmZhLTY4OWItNGQ1Yi1iYmYyLTM5ZGRlNjRjY2I2NA==");
 		con.setRequestProperty("Content-Type", "application/json");

 		//add request header
 		//con.setRequestProperty("User-Agent", USER_AGENT);

 		int responseCode = con.getResponseCode();
 		//System.out.println("\nSending 'GET' request to URL : " + url);
 		System.out.println("Response Code : " + responseCode);

 		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
 		String inputLine;
 		StringBuffer response = new StringBuffer();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		in.close();

 		//print result
 		//System.out.println(response.toString());
 		jsonToken(response.toString());
 		//jsonToken(response.toString());
		return "save";
	}
	@Autowired
	StandardService sd;
	StandardDao standardDao;
	@RequestMapping("/standard")
	public String standard() {
		Yng_Standard standard=new Yng_Standard();
		//standard.setStandardId(standardId);
		/*standard.setKey("Client_id");
		standard.setValue("e9ed82a8-4237-4185-8e36-47264aa9e718");
		standard.setType("String");
		standard.setDescription("username");
		standardDao.save(standard);*/
		
		/*standard.setKey("Client_secret");
		standard.setValue("b1ed1bfa-689b-4d5b-bbf2-39dde64ccb64");
		standard.setType("String");
		standard.setDescription("password");
		standardDao.save(standard);*/
		Long a=(long) 2012;
		
		standard=sd.findByItemId(a);
		System.out.println("standard:"+standard.toString());
		return "save";
	}
	String TOKEN=""; 
	private void jsonToken(String json) {
        ObjectMapper mapper = new ObjectMapper();
        AccessTokenDHL token;
		try {
			token = mapper.readValue(json, AccessTokenDHL.class);
			// System.out.println(token.toString());
			 System.out.println("token:"+token.toString());
			 TOKEN=token.getAccess_token();
			 
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
	}
	private void jsonShipments(String json) {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
       // mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        DhlResponse shipments;
		try {
			shipments = mapper.readValue(json, DhlResponse.class);
			
			// System.out.println(token.toString());
			 System.out.println("shipments:"+shipments.toString());
			 jsonToShipments(shipments);
			 //System.out.println("getPickupAccount:"+shipments.get));
			 //TOKEN=shipments.;			 
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	private void jsonToShipments(DhlResponse shipments) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		  
		String json = mapper.writerWithDefaultPrettyPrinter()
		                    .writeValueAsString(shipments);

		System.out.println("SALIDA JSON: \n" + json);
		
	}
}
