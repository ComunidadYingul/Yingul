package com.valework.yingul.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valework.yingul.dao.AccountDao;
import com.valework.yingul.dao.ConfirmDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.TransactionDao;
import com.valework.yingul.logistic.AccessTokenDHL;
import com.valework.yingul.model.Yng_Account;
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_Transaction;

@Component
public class GreetingBatchBean {
	
	@Autowired
	ConfirmDao confirmDao;
	
	@Autowired
	TransactionDao transactionDao;
	
	@Autowired
	AccountDao accountDao;
	@Autowired
	StandardDao standardDao;
	
	@Scheduled(cron = "0,30 * * * * *")//para cada 30 segundos
	//@Scheduled(cron = "0 0 6 * * *")//cada dia a las 6 de la mañana
	public void cronJob() {
		Date date = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd");
    	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
    	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
		List<Yng_Confirm> listConfirm = confirmDao.findByStatus("confirm");
		for (Yng_Confirm s : listConfirm) {
			if(s.getDayEndClaim()<=Integer.parseInt(hourdateFormat.format(date))&&s.getMonthEndClaim()<=Integer.parseInt(hourdateFormat1.format(date))&&s.getYearEndClaim()<=Integer.parseInt(hourdateFormat2.format(date))) {
				s.setStatus("closed");
				Yng_Account accountTemp= accountDao.findByUser(s.getBuy().getYng_item().getUser());
				Yng_Transaction transactionTemp = new Yng_Transaction();
				transactionTemp.setAccount(accountTemp);
				transactionTemp.setAmount(s.getBuy().getYng_item().getPrice());
				transactionTemp.setCity("Moreno");
				transactionTemp.setCountry("Argentina");
				transactionTemp.setCountryCode("AR");
				transactionTemp.setCurrency("ARS");
				transactionTemp.setDay(Integer.parseInt(hourdateFormat.format(date)));
				transactionTemp.setDescription("Acreditación por venta del producto ");
				transactionTemp.setIp("181.115.199.143");
				transactionTemp.setLat("-16.5");
				transactionTemp.setLon("-68.15");
				transactionTemp.setMonth(Integer.parseInt(hourdateFormat1.format(date)));
				transactionTemp.setOrg("Entel S.A. - EntelNet");
				transactionTemp.setRegionName("Buenos Aires");
				transactionTemp.setType("Acreditacion");
				transactionTemp.setYear(Integer.parseInt(hourdateFormat2.format(date)));
				transactionTemp.setZip("1744");
				accountTemp.setAvailableMoney(s.getBuy().getYng_item().getPrice());
				accountDao.save(accountTemp);
				transactionDao.save(transactionTemp);
				confirmDao.save(s);
			}
    	}
	}
	/*@Scheduled(cron = "0,55 * * * * *")//para cada 45 s
	public void con() throws IOException {
		String urlDomiciliio="https://private-anon-ee29acebcf-efulfillment1.apiary-proxy.com/efulfillment/v1/auth/accesstoken";

 		
 		URL obj = new URL(urlDomiciliio);
 		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 		 //System.out.println("token:"+token.toString());
			Yng_Standard standard=new Yng_Standard();
			//standard.setStandardId(standardId);
			standard=standardDao.findByKey("Client_id");
			String Client_id=""+standard.getValue();
			standard=standardDao.findByKey("Client_secret");
			String Client_secret=""+standard.getValue();
			org.apache.commons.codec.binary.Base64 decoder = new org.apache.commons.codec.binary.Base64();
			byte[] decodedBytes = decoder.decode("ZTllZDgyYTgtNDIzNy00MTg1LThlMzYtNDcyNjRhYTllNzE4OmIxZWQxYmZhLTY4OWItNGQ1Yi1iYmYyLTM5ZGRlNjRjY2I2NA==");
			System.out.println(new String(decodedBytes) + "\n") ;
			byte[] encodedBytes = org.apache.commons.codec.binary.Base64.encodeBase64((Client_id+":"+Client_secret).getBytes());
			String base64CIS=new String(encodedBytes);
			System.out.println("keytokken: " + new String(encodedBytes));
			byte[] decodedBytes1 = org.apache.commons.codec.binary.Base64.decodeBase64(encodedBytes);
			System.out.println("decodedBytes: " + new String(decodedBytes1));
 		// optional default is GET
 		con.setRequestMethod("GET");
 		con.setRequestProperty("Accept", "application/json");
 		con.setRequestProperty("Authorization", "Basic "+base64CIS);
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

 		jsonToken(response.toString());
		System.out.println("anime");
	}
	*/
	private void jsonToken(String json) {
        ObjectMapper mapper = new ObjectMapper();
        AccessTokenDHL token;
		try {
			token = mapper.readValue(json, AccessTokenDHL.class);
			// System.out.println(token.toString());
			 System.out.println("token:"+token.toString());
				Yng_Standard standard=new Yng_Standard();
				//standard.setStandardId(standardId);
				standard=standardDao.findByKey("access_token");
				standard.setKey("access_token");
				standard.setValue(token.getAccess_token());
				standard.setType("String");
				standard.setDescription("token dhl");
				standardDao.save(standard);
			 //TOKEN=token.getAccess_token();
			 
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
	
}
