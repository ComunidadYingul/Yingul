package com.valework.yingul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.valework.yingul.dao.CardDao;
import com.valework.yingul.dao.CardProviderDao;
import com.valework.yingul.dao.RequestBodyDao;
import com.valework.yingul.dao.RequestDao;
import com.valework.yingul.dao.ResponseBodyDao;
import com.valework.yingul.dao.ResponseDao;
import com.valework.yingul.dao.ResponseHeaderDao;
import com.valework.yingul.dao.StandardDao;
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
import com.valework.yingul.service.PersonService;
import com.valework.yingul.util.VisaAPIClient;

@Component
public class PayUFunds {
	final static Logger logger = Logger.getLogger(VisaAPIClient.class);
	@Autowired 
	CardProviderDao cardProviderDao;
	@Autowired 
	CardDao cardDao;
	@Autowired 
	RequestDao requestDao;
	@Autowired 
	RequestBodyDao requestBodyDao;
	@Autowired 
	ResponseDao responseDao;
	@Autowired
	ResponseHeaderDao responseHeaderDao;
	@Autowired
	ResponseBodyDao responseBodyDao;
	@Autowired 
	StandardDao standardDao;
	@Autowired 
	PersonService personService;
	
	public Yng_Payment authorizeCard(Yng_Buy buy, Yng_User userTemp) throws Exception, ClientProtocolException, IOException{
		//Datos Principales del comprador 
		List<Yng_Person> personList= personService.findByUser(buy.getUser());
		Yng_Person person = personList.get(0);
		//recuoerar los stanadarts
		Yng_Standard api = standardDao.findByKey("PAYU_api");
		Yng_Standard test = standardDao.findByKey("PAYU_test");
		Yng_Standard apikey = standardDao.findByKey("PAYU_apiKey");
		Yng_Standard apiLogin = standardDao.findByKey("PAYU_apiLogin");
		Yng_Standard accountId = standardDao.findByKey("PAYU_accountId");
		Yng_Standard INSTALLMENTS_NUMBER = standardDao.findByKey("PAYU_INSTALLMENTS_NUMBER");
		Yng_Standard merchantId = standardDao.findByKey("PAYU_merchantId");
		//crear el referenceCode y el signature
		Date time = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");
		String referenceCode="Yingul"+hourdateFormat.format(time);
		String bSignature=apikey.getValue()+"~"+merchantId.getValue()+"~"+referenceCode+"~"+buy.getCost()+"~ARS";
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(bSignature.getBytes());
	    byte[] digest = md.digest();
	    String signature = DatatypeConverter
	      .printHexBinary(digest).toUpperCase();
		// crear el request 
		Yng_Request requestTemp = new Yng_Request(); 
		requestTemp.setURI(api.getValue());
		requestTemp.setInfo("Payment Authorization Test");
		requestTemp = requestDao.save(requestTemp);
		//crear el response
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(api.getValue());
	    String json = "{\r\n" + 
	    		"   \"language\": \"es\",\r\n" + 
	    		"   \"command\": \"SUBMIT_TRANSACTION\",\r\n" + 
	    		"   \"merchant\": {\r\n" + 
	    		"      \"apiKey\": \""+apikey.getValue()+"\",\r\n" + 
	    		"      \"apiLogin\": \""+apiLogin.getValue()+"\"\r\n" + 
	    		"   },\r\n" + 
	    		"   \"transaction\": {\r\n" + 
	    		"      \"order\": {\r\n" + 
	    		"         \"accountId\": \""+accountId.getValue()+"\",\r\n" + 
	    		"         \"referenceCode\": \""+referenceCode+"\",\r\n" + 
	    		"         \"description\": \"payment test\",\r\n" + 
	    		"         \"language\": \"es\",\r\n" + 
	    		"         \"signature\": \""+signature+"\",\r\n" + 
	    		"         \"notifyUrl\": \"http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com\",\r\n" + 
	    		"         \"additionalValues\": {\r\n" + 
	    		"            \"TX_VALUE\": {\r\n" + 
	    		"               \"value\": "+buy.getCost()+",\r\n" + 
	    		"               \"currency\": \"ARS\"\r\n" + 
	    		"            }\r\n" + 
	    		"         },\r\n" + 
	    		"         \"buyer\": {\r\n" + 
	    		"            \"merchantBuyerId\": \""+buy.getUser().getUserId()+"\",\r\n" + 
	    		"            \"fullName\": \""+person.getName()+" "+person.getLastname()+"\",\r\n" + 
	    		"            \"emailAddress\": \""+buy.getUser().getEmail()+"\",\r\n" + 
	    		"            \"contactPhone\": \""+buy.getUser().getPhone()+"\",\r\n" + 
	    		"            \"dniNumber\": \"5415668464654\",\r\n" + 
	    		"            \"shippingAddress\": {\r\n" + 
	    		"               \"street1\": \"Viamonte\",\r\n" + 
	    		"               \"street2\": \"1366\",\r\n" + 
	    		"               \"city\": \"Buenos Aires\",\r\n" + 
	    		"               \"state\": \"Buenos Aires\",\r\n" + 
	    		"               \"country\": \"AR\",\r\n" + 
	    		"               \"postalCode\": \"000000\",\r\n" + 
	    		"               \"phone\": \"7563126\"\r\n" + 
	    		"            }\r\n" + 
	    		"         },\r\n" + 
	    		"         \"shippingAddress\": {\r\n" + 
	    		"            \"street1\": \"Viamonte\",\r\n" + 
	    		"            \"street2\": \"1366\",\r\n" + 
	    		"            \"city\": \"Buenos Aires\",\r\n" + 
	    		"            \"state\": \"Buenos Aires\",\r\n" + 
	    		"            \"country\": \"AR\",\r\n" + 
	    		"            \"postalCode\": \"0000000\",\r\n" + 
	    		"            \"phone\": \"7563126\"\r\n" + 
	    		"         }\r\n" + 
	    		"      },\r\n" + 
	    		"      \"payer\": {\r\n" + 
	    		"         \"merchantPayerId\": \""+buy.getUser().getUserId()+"\",\r\n" + 
	    		"         \"fullName\": \""+person.getName()+" "+person.getLastname()+"\",\r\n" + 
	    		"         \"emailAddress\": \""+buy.getUser().getEmail()+"\",\r\n" + 
	    		"         \"contactPhone\": \""+buy.getUser().getPhone()+"\",\r\n" + 
	    		"         \"dniNumber\": \"5415668464654\",\r\n" + 
	    		"         \"billingAddress\": {\r\n" + 
	    		"            \"street1\": \""+buy.getUser().getYng_Ubication().getStreet()+"\",\r\n" + 
	    		"            \"street2\": \""+buy.getUser().getYng_Ubication().getNumber()+"\",\r\n" + 
	    		"            \"city\": \""+buy.getUser().getYng_Ubication().getYng_City().getName()+"\",\r\n" + 
	    		"            \"state\": \""+buy.getUser().getYng_Ubication().getYng_Province().getName()+"\",\r\n" + 
	    		"            \"country\": \"AR\",\r\n" + 
	    		"            \"postalCode\": \""+buy.getUser().getYng_Ubication().getUbicationId()+"\",\r\n" + 
	    		"            \"phone\": \""+buy.getUser().getPhone()+"\"\r\n" + 
	    		"         }\r\n" + 
	    		"      },\r\n" + 
	    		"      \"creditCard\": {\r\n" + 
	    		"         \"number\": \""+buy.getYng_Payment().getYng_Card().getNumber()+"\",\r\n" + 
	    		"         \"securityCode\": \""+buy.getYng_Payment().getYng_Card().getSecurityCode()+"\",\r\n" + 
	    		"         \"expirationDate\": \""+buy.getYng_Payment().getYng_Card().getDueYear()+"/";
	    		if(buy.getYng_Payment().getYng_Card().getDueMonth()>=10) {
	    			json+=buy.getYng_Payment().getYng_Card().getDueMonth();
	    		}else{
	    			json+="0"+buy.getYng_Payment().getYng_Card().getDueMonth();
	    		}
	    		json+="\",\r\n" + 
	    		"         \"name\": \""+buy.getYng_Payment().getYng_Card().getFullName().trim().toUpperCase()+"\"\r\n" + 
	    		"      },\r\n" + 
	    		"      \"extraParameters\": {\r\n" + 
	    		"         \"INSTALLMENTS_NUMBER\": "+INSTALLMENTS_NUMBER.getValue()+"\r\n" + 
	    		"      },\r\n" + 
	    		"      \"type\": \"AUTHORIZATION\",\r\n" + 
	    		"      \"paymentMethod\": \""+buy.getYng_Payment().getYng_Card().getProvider()+"\",\r\n" + 
	    		"      \"paymentCountry\": \"AR\",\r\n" + 
	    		"      \"deviceSessionId\": \"vghs6tvkcle931686k1900o6e1\",\r\n" + 
	    		"      \"ipAddress\": \""+buy.getIp()+"\",\r\n" + 
	    		"      \"cookie\": \"pt1t38347bs6jc9ruv2ecpv7o2\",\r\n" + 
	    		"      \"userAgent\": \""+buy.getUserAgent()+"\"\r\n" + 
	    		"   },\r\n" + 
	    		"   \"test\": "+test.getValue()+"\r\n" + 
	    		"}";
	    
	    Yng_RequestBody body= new Yng_RequestBody(); 
	    body.setKey("body");
	    body.setValue(json);
	    body.setRequest(requestTemp);
		requestBodyDao.save(body);
	    
	    StringEntity entity = new StringEntity(json);
	    httpPost.setEntity(entity);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-type", "application/json");
	 
	    CloseableHttpResponse response = client.execute(httpPost);
	    Yng_Response responseTemp = this.logResponse(response);
	   
	    Set<Yng_ResponseHeader> responseHeader=responseTemp.getResponseHeader();
    	Set<Yng_ResponseBody> responseBody=responseTemp.getResponseBody();
    	responseTemp.setResponseHeader(null);
    	responseTemp.setResponseBody(null);
    	responseTemp=responseDao.save(responseTemp);
    	requestTemp.setYng_Response(responseTemp);
    	requestTemp=requestDao.save(requestTemp);
        response.close();
	    client.close();
		//
	    for (Yng_ResponseHeader s : responseHeader) {
    		s.setResponse(responseTemp);
    		responseHeaderDao.save(s);
    	}
    	for(Yng_ResponseBody t:responseBody) {
    		t.setResponse(responseTemp);
    		responseBodyDao.save(t);
    	}
    	
    	
    	for(Yng_ResponseBody t:responseBody) {
    		t.setResponse(responseTemp);
    		if(t.getKey().equals("transactionResponse")) {
    			if(t.getValue().equals("null")) {
    				return null;
    			}else {
    				JSONObject  jObject = new JSONObject(t.getValue());
    				Map<String,String> map = new HashMap<String,String>();
    				Iterator iter = jObject.keys();
	                while(iter.hasNext()){
	                    String key = (String)iter.next();
	                    String value= jObject.get(key).toString();
	                    map.put(key,value);
	                    if(key.equals("responseCode")) {
	                    	if(value.equals("APPROVED")) {
	                    		//guardar la tarjeta
	                    		Yng_Card cardTemp=buy.getYng_Payment().getYng_Card();
	                    		cardTemp.setFullName(cardTemp.getFullName().trim().toUpperCase());
	                    		cardTemp.setUser(userTemp);
	                    		if(cardTemp.getType().toString().equals("DEBIT"))
	                    		{
	                    			cardTemp.setYng_CardProvider(null);
	                    		}
	                    		else {
	                    			cardTemp.setYng_CardProvider(cardProviderDao.findByCardProviderId(cardTemp.getYng_CardProvider().getCardProviderId()));
	                    		}
	                    		Yng_Payment paymentTemp=buy.getYng_Payment();
	                    		//para ver si la tarjeta existe 
	                    		if (null == cardDao.findByNumberAndUser(cardTemp.getNumber(),buy.getUser())) {
	                    			paymentTemp.setYng_Card(cardDao.save(cardTemp)); 
	                            }
	                    		else {
	                    			paymentTemp.setYng_Card(cardTemp);
	                    		}
	                    		paymentTemp.setYng_Request(requestTemp);
	                    		
	                    		return paymentTemp;
	                    		
	                    	}
	                    	else {
	                    		return null;
	                    	}
	                    }
	                }
    			}
    		}
    	}
    	return null;

	}

	
	public Yng_Response logResponse(CloseableHttpResponse response) throws IOException, JSONException {
		Yng_Response responseTemp= new Yng_Response();
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
        Set<Yng_ResponseHeader> responseHeader = new HashSet<>();
        logger.info("Response Headers: \n");
        for (int i = 0; i < h.length; i++) {
            logger.info(h[i].getName() + ":" + h[i].getValue());
            Yng_ResponseHeader rhTemp= new Yng_ResponseHeader();
            rhTemp.setName(h[i].getName());
            rhTemp.setValue(h[i].getValue());
            responseHeader.add(rhTemp);
        }
        responseTemp.setResponseHeader(responseHeader);
        logger.info("\n Response Body:");
        
        if(!StringUtils.isEmpty(result.toString())) {
            ObjectMapper mapper = getObjectMapperInstance();
            Object tree;
            try {
                tree = mapper.readValue(result.toString(), Object.class);
                logger.info("ResponseBody: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree));
                String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree);
                JSONObject  jObject = new JSONObject(s);

                Map<String,String> map = new HashMap<String,String>();
                Iterator iter = jObject.keys();
                Set<Yng_ResponseBody> responseBody = new HashSet<>();
                while(iter.hasNext()){
                    String key = (String)iter.next();
                    String value= jObject.get(key).toString();
                    map.put(key,value);
                    Yng_ResponseBody rbTemp= new Yng_ResponseBody();
                    rbTemp.setKey(key);
                    rbTemp.setValue(value);
                    responseBody.add(rbTemp);
                }
                responseTemp.setResponseBody(responseBody);
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
}
