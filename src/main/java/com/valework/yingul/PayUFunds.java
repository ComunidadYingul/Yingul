package com.valework.yingul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
import com.valework.yingul.model.Yng_Buy;
import com.valework.yingul.model.Yng_Card;
import com.valework.yingul.model.Yng_Payment;
import com.valework.yingul.model.Yng_Request;
import com.valework.yingul.model.Yng_RequestBody;
import com.valework.yingul.model.Yng_Response;
import com.valework.yingul.model.Yng_ResponseBody;
import com.valework.yingul.model.Yng_ResponseHeader;
import com.valework.yingul.model.Yng_User;
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
	
	public Yng_Payment authorizeCard(Yng_Buy buy, Yng_User userTemp) throws Exception, ClientProtocolException, IOException{
		// crear el request 
		Yng_Request requestTemp = new Yng_Request(); 
		requestTemp.setURI("https://sandbox.api.payulatam.com/payments-api/4.0/service.cgi");
		requestTemp.setInfo("Payment Authorization Test");
		requestTemp = requestDao.save(requestTemp);
		
		Yng_RequestBody language= new Yng_RequestBody(); 
		language.setKey("language");
		language.setValue("es");
		language.setRequest(requestTemp);
		requestBodyDao.save(language);
		
		Yng_RequestBody command= new Yng_RequestBody(); 
		command.setKey("command");
		command.setValue("SUBMIT_TRANSACTION");
		command.setRequest(requestTemp);
		requestBodyDao.save(command);
		
		Yng_RequestBody merchant= new Yng_RequestBody(); 
		merchant.setKey("merchant");
		merchant.setValue("{\r\n" + 
				"\"apiKey\":\"4Vj8eK4rloUd272L48hsrarnUA\",\r\n" + 
				"\"apiLogin\":\"pRRXKOl8ikMmt9u\"\r\n" + 
				"}");
		merchant.setRequest(requestTemp);
		requestBodyDao.save(merchant);
		
		Yng_RequestBody transaction= new Yng_RequestBody(); 
		transaction.setKey("transaction");
		transaction.setValue("{\r\n" + 
				"\"order\":{\r\n" + 
				"\"accountId\":\"512322\",\r\n" + 
				"\"referenceCode\":\"2018/02/2711:00\",\r\n" + 
				"\"description\":\"payment test\",\r\n" + 
				"\"language\":\"es\",\r\n" + 
				"\"signature\":\"0656fa6012fb9d81bd1b1080ae0c8385\",\r\n" + 
				"\"notifyUrl\":\"http://www.tes.com/confirmation\",\r\n" + 
				"\"additionalValues\":{\r\n" + 
				"\"TX_VALUE\":{\r\n" + 
				"\"value\":100,\r\n" + 
				"\"currency\":\"ARS\"\r\n" + 
				"}\r\n" + 
				"},\r\n" + 
				"\"buyer\":{\r\n" + 
				"\"merchantBuyerId\":\"1\",\r\n" + 
				"\"fullName\":\"First name and second buyer name\",\r\n" + 
				"\"emailAddress\":\"buyer_test@test.com\",\r\n" + 
				"\"contactPhone\":\"7563126\",\r\n" + 
				"\"dniNumber\":\"5415668464654\",\r\n" + 
				"\"shippingAddress\":{\r\n" + 
				"\"street1\":\"Viamonte\",\r\n" + 
				"\"street2\":\"1366\",\r\n" + 
				"\"city\":\"Buenos Aires\",\r\n" + 
				"\"state\":\"Buenos Aires\",\r\n" + 
				"\"country\":\"AR\",\r\n" + 
				"\"postalCode\":\"000000\",\r\n" + 
				"\"phone\":\"7563126\"\r\n" + 
				"}\r\n" + 
				"},\r\n" + 
				"\"shippingAddress\":{\r\n" + 
				"\"street1\":\"Viamonte\",\r\n" + 
				"\"street2\":\"1366\",\r\n" + 
				"\"city\":\"Buenos Aires\",\r\n" + 
				"\"state\":\"Buenos Aires\",\r\n" + 
				"\"country\":\"AR\",\r\n" + 
				"\"postalCode\":\"0000000\",\r\n" + 
				"\"phone\":\"7563126\"\r\n" + 
				"}\r\n" + 
				"},\r\n" + 
				"\"payer\":{\r\n" + 
				"\"merchantPayerId\":\"1\",\r\n" + 
				"\"fullName\":\"First name and second payer name\",\r\n" + 
				"\"emailAddress\":\"payer_test@test.com\",\r\n" + 
				"\"contactPhone\":\"7563126\",\r\n" + 
				"\"dniNumber\":\"5415668464654\",\r\n" + 
				"\"billingAddress\":{\r\n" + 
				"\"street1\":\"Avenida entre rios\",\r\n" + 
				"\"street2\":\"452\",\r\n" + 
				"\"city\":\"Plata\",\r\n" + 
				"\"state\":\"Buenos Aires\",\r\n" + 
				"\"country\":\"AR\",\r\n" + 
				"\"postalCode\":\"64000\",\r\n" + 
				"\"phone\":\"7563126\"\r\n" + 
				"}\r\n" + 
				"},\r\n" + 
				"\"creditCard\":{\r\n" + 
				"\"number\":\"4850110000000000\",\r\n" + 
				"\"securityCode\":\"321\",\r\n" + 
				"\"expirationDate\":\"2018/12\",\r\n" + 
				"\"name\":\"APPROVED\"\r\n" + 
				"},\r\n" + 
				"\"extraParameters\":{\r\n" + 
				"\"INSTALLMENTS_NUMBER\":1\r\n" + 
				"},\r\n" + 
				"\"type\":\"AUTHORIZATION\",\r\n" + 
				"\"paymentMethod\":\"VISA\",\r\n" + 
				"\"paymentCountry\":\"AR\",\r\n" + 
				"\"deviceSessionId\":\"vghs6tvkcle931686k1900o6e1\",\r\n" + 
				"\"ipAddress\":\"127.0.0.1\",\r\n" + 
				"\"cookie\":\"pt1t38347bs6jc9ruv2ecpv7o2\",\r\n" + 
				"\"userAgent\":\"Mozilla/5.0 (Windows NT 5.1; rv:18.0) Gecko/20100101 Firefox/18.0\"\r\n" + 
				"}");
		transaction.setRequest(requestTemp);
		requestBodyDao.save(transaction);
		
		Yng_RequestBody test= new Yng_RequestBody(); 
		test.setKey("test");
		test.setValue("true");
		test.setRequest(requestTemp);
		requestBodyDao.save(command);
		//
		
		//crear el response
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost("https://sandbox.api.payulatam.com/payments-api/4.0/service.cgi");
	 
	    String json = "{\r\n" + 
	    		"   \"language\": \"es\",\r\n" + 
	    		"   \"command\": \"SUBMIT_TRANSACTION\",\r\n" + 
	    		"   \"merchant\": {\r\n" + 
	    		"      \"apiKey\": \"4Vj8eK4rloUd272L48hsrarnUA\",\r\n" + 
	    		"      \"apiLogin\": \"pRRXKOl8ikMmt9u\"\r\n" + 
	    		"   },\r\n" + 
	    		"   \"transaction\": {\r\n" + 
	    		"      \"order\": {\r\n" + 
	    		"         \"accountId\": \"512322\",\r\n" + 
	    		"         \"referenceCode\": \"2018/02/2711:00\",\r\n" + 
	    		"         \"description\": \"payment test\",\r\n" + 
	    		"         \"language\": \"es\",\r\n" + 
	    		"         \"signature\": \"0656fa6012fb9d81bd1b1080ae0c8385\",\r\n" + 
	    		"         \"notifyUrl\": \"http://www.tes.com/confirmation\",\r\n" + 
	    		"         \"additionalValues\": {\r\n" + 
	    		"            \"TX_VALUE\": {\r\n" + 
	    		"               \"value\": 100,\r\n" + 
	    		"               \"currency\": \"ARS\"\r\n" + 
	    		"            }\r\n" + 
	    		"         },\r\n" + 
	    		"         \"buyer\": {\r\n" + 
	    		"            \"merchantBuyerId\": \"1\",\r\n" + 
	    		"            \"fullName\": \"First name and second buyer  name\",\r\n" + 
	    		"            \"emailAddress\": \"buyer_test@test.com\",\r\n" + 
	    		"            \"contactPhone\": \"7563126\",\r\n" + 
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
	    		"         \"merchantPayerId\": \"1\",\r\n" + 
	    		"         \"fullName\": \"First name and second payer name\",\r\n" + 
	    		"         \"emailAddress\": \"payer_test@test.com\",\r\n" + 
	    		"         \"contactPhone\": \"7563126\",\r\n" + 
	    		"         \"dniNumber\": \"5415668464654\",\r\n" + 
	    		"         \"billingAddress\": {\r\n" + 
	    		"            \"street1\": \"Avenida entre rios\",\r\n" + 
	    		"            \"street2\": \"452\",\r\n" + 
	    		"            \"city\": \"Plata\",\r\n" + 
	    		"            \"state\": \"Buenos Aires\",\r\n" + 
	    		"            \"country\": \"AR\",\r\n" + 
	    		"            \"postalCode\": \"64000\",\r\n" + 
	    		"            \"phone\": \"7563126\"\r\n" + 
	    		"         }\r\n" + 
	    		"      },\r\n" + 
	    		"      \"creditCard\": {\r\n" + 
	    		"         \"number\": \"4850110000000000\",\r\n" + 
	    		"         \"securityCode\": \"321\",\r\n" + 
	    		"         \"expirationDate\": \"2018/12\",\r\n" + 
	    		"         \"name\": \"APPROVED\"\r\n" + 
	    		"      },\r\n" + 
	    		"      \"extraParameters\": {\r\n" + 
	    		"         \"INSTALLMENTS_NUMBER\": 1\r\n" + 
	    		"      },\r\n" + 
	    		"      \"type\": \"AUTHORIZATION\",\r\n" + 
	    		"      \"paymentMethod\": \"VISA\",\r\n" + 
	    		"      \"paymentCountry\": \"AR\",\r\n" + 
	    		"      \"deviceSessionId\": \"vghs6tvkcle931686k1900o6e1\",\r\n" + 
	    		"      \"ipAddress\": \"127.0.0.1\",\r\n" + 
	    		"      \"cookie\": \"pt1t38347bs6jc9ruv2ecpv7o2\",\r\n" + 
	    		"      \"userAgent\": \"Mozilla/5.0 (Windows NT 5.1; rv:18.0) Gecko/20100101 Firefox/18.0\"\r\n" + 
	    		"   },\r\n" + 
	    		"   \"test\": true\r\n" + 
	    		"}";
	    StringEntity entity = new StringEntity(json);
	    httpPost.setEntity(entity);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-type", "application/json");
	 
	    CloseableHttpResponse response = client.execute(httpPost);
	    Yng_Response responseTemp = this.logResponse(response);
	    
        response.close();
	    client.close();
		//
		
		
		//guardar la tarjeta
		Yng_Card cardTemp=buy.getYng_Payment().getYng_Card();
		cardTemp.setFullName(cardTemp.getFullName().trim().toUpperCase());
		cardTemp.setUser(userTemp);
		cardTemp.setDueYear(cardTemp.getDueYear()%100);
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
