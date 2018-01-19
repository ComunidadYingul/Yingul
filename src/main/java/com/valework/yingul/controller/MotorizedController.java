package com.valework.yingul.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStreamReader;

//import java.net.URL;
import java.net.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.model.Yng_Security;
import com.valework.yingul.service.SecurityMotorized;
import org.apache.commons.codec.binary.Base64;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;




@RestController
@RequestMapping("/motorized")
public class MotorizedController {
	String token="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXUyJ9.eyJleHAiOjE1MTI4NDA3MzcsInVzZXJuYW1lIjoiNDZlOTAzZjJhM2UxY2IxOTMzZGI4MzZiOTlkZjA4OWY5MWU5ZmQ1NyIsImlhdCI6IjE1MTI4MjYzMzcifQ.NYUk7AKJI0oPt0okRKkmnAaMvv8jgt5jMby2al0HeeVzobwhFbm-uj9Acfvhgl-Yf7Px8NEY9RSbIdhGU36mvyOgq2jGl9anjt4uIBmMTaR6OaNQ4IYzpoVc7ZouUbg-qZqj5bRmLIAIq_ckFwnwagtotYYm_qyYUc1Yi4xboaG81FqAZSpALGpF3FM8Z8neN6dtvRE_sgzMVgnPVcLe4FsExUKZS4BmPLraO94rOpT_p61WOl8xvJ2vKHWiRDiqJ2Kryb66aij6I8crj6EECvOR5zv7gcMa7Szzrg4eFkCVuZBwRak3SUg3F-VKENczUZ9L9rWOLJdxYyclRQlFZMXLdYkNyCgZJUY5Q4Fh1JdFg7S0Cj8VuJ5Jmdtx8HyqxDfPEngAnkASa9xdXi8v2jW1PVD4Q9r-B1gA2fF4I8PwjSwsk4uvdQ-SDoI-eitXq_AXGXUAzBohavRjvtrgq0zI-Ez0anbTMQ_D18bDWTgwtAFljHN5sLiMjLEuDtZjhodPX9jjSqn_s_S45Pkd5oO1ikYryQgO-2VggpKc3w40b7dDLBcHGFqpDsfaRbgdq6I-4MHgmg3X2un4XAMq4ZAdMl4kpg99cO6-2M1X6p7AAdp59Cy7TTQMrI-wA0uDq4FXVWY8mp7q9cXeMg2bJTjCm6z9SnwDn-4w_mN_S_A";
		
	@Autowired
	private SecurityMotorized securityMotorized;
	
	@RequestMapping("/security")
	public List<Yng_Security> findSecurytiList() {
	        List<Yng_Security> securityList = securityMotorized.findAll();
	        return securityList;
	}
 
    
    @RequestMapping("/da")
    private String daniel() {
    	String da="guardado";
    	MotorizedController http = new MotorizedController();

		System.out.println("Testing 1 - Send Http GET request");
		try {
			http.sendGet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*
		System.out.println("\nTesting 2 - Send Http POST request");
		try {
			http.sendPost();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

  */  	
    	return da;
    }
    private final String USER_AGENT = "Mozilla/5.0";
    
 // HTTP GET request
 	private void sendGet() throws Exception {

 		//String url = "http://www.google.com/search?q=mkyong";
 		//String urlCotizar="https://api.enviopack.com/cotizar/costo?access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXUyJ9.eyJleHAiOjE1MTI4NDA3MzcsInVzZXJuYW1lIjoiNDZlOTAzZjJhM2UxY2IxOTMzZGI4MzZiOTlkZjA4OWY5MWU5ZmQ1NyIsImlhdCI6IjE1MTI4MjYzMzcifQ.NYUk7AKJI0oPt0okRKkmnAaMvv8jgt5jMby2al0HeeVzobwhFbm-uj9Acfvhgl-Yf7Px8NEY9RSbIdhGU36mvyOgq2jGl9anjt4uIBmMTaR6OaNQ4IYzpoVc7ZouUbg-qZqj5bRmLIAIq_ckFwnwagtotYYm_qyYUc1Yi4xboaG81FqAZSpALGpF3FM8Z8neN6dtvRE_sgzMVgnPVcLe4FsExUKZS4BmPLraO94rOpT_p61WOl8xvJ2vKHWiRDiqJ2Kryb66aij6I8crj6EECvOR5zv7gcMa7Szzrg4eFkCVuZBwRak3SUg3F-VKENczUZ9L9rWOLJdxYyclRQlFZMXLdYkNyCgZJUY5Q4Fh1JdFg7S0Cj8VuJ5Jmdtx8HyqxDfPEngAnkASa9xdXi8v2jW1PVD4Q9r-B1gA2fF4I8PwjSwsk4uvdQ-SDoI-eitXq_AXGXUAzBohavRjvtrgq0zI-Ez0anbTMQ_D18bDWTgwtAFljHN5sLiMjLEuDtZjhodPX9jjSqn_s_S45Pkd5oO1ikYryQgO-2VggpKc3w40b7dDLBcHGFqpDsfaRbgdq6I-4MHgmg3X2un4XAMq4ZAdMl4kpg99cO6-2M1X6p7AAdp59Cy7TTQMrI-wA0uDq4FXVWY8mp7q9cXeMg2bJTjCm6z9SnwDn-4w_mN_S_A&provincia=C&codigo_postal=1405&peso=1.5&paquetes=20x3x5&direccion_envio=829";
 		String urlDomiciliio="https://api.enviopack.com/cotizar/precio/a-domicilio?access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXUyJ9.eyJleHAiOjE1MTI4NDA3MzcsInVzZXJuYW1lIjoiNDZlOTAzZjJhM2UxY2IxOTMzZGI4MzZiOTlkZjA4OWY5MWU5ZmQ1NyIsImlhdCI6IjE1MTI4MjYzMzcifQ.NYUk7AKJI0oPt0okRKkmnAaMvv8jgt5jMby2al0HeeVzobwhFbm-uj9Acfvhgl-Yf7Px8NEY9RSbIdhGU36mvyOgq2jGl9anjt4uIBmMTaR6OaNQ4IYzpoVc7ZouUbg-qZqj5bRmLIAIq_ckFwnwagtotYYm_qyYUc1Yi4xboaG81FqAZSpALGpF3FM8Z8neN6dtvRE_sgzMVgnPVcLe4FsExUKZS4BmPLraO94rOpT_p61WOl8xvJ2vKHWiRDiqJ2Kryb66aij6I8crj6EECvOR5zv7gcMa7Szzrg4eFkCVuZBwRak3SUg3F-VKENczUZ9L9rWOLJdxYyclRQlFZMXLdYkNyCgZJUY5Q4Fh1JdFg7S0Cj8VuJ5Jmdtx8HyqxDfPEngAnkASa9xdXi8v2jW1PVD4Q9r-B1gA2fF4I8PwjSwsk4uvdQ-SDoI-eitXq_AXGXUAzBohavRjvtrgq0zI-Ez0anbTMQ_D18bDWTgwtAFljHN5sLiMjLEuDtZjhodPX9jjSqn_s_S45Pkd5oO1ikYryQgO-2VggpKc3w40b7dDLBcHGFqpDsfaRbgdq6I-4MHgmg3X2un4XAMq4ZAdMl4kpg99cO6-2M1X6p7AAdp59Cy7TTQMrI-wA0uDq4FXVWY8mp7q9cXeMg2bJTjCm6z9SnwDn-4w_mN_S_A&provincia=C&codigo_postal=1414&peso=1.5";

 		URL obj = new URL(urlDomiciliio);
 		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

 		// optional default is GET
 		con.setRequestMethod("GET");

 		//add request header
 		con.setRequestProperty("User-Agent", USER_AGENT);

 		int responseCode = con.getResponseCode();
 		//System.out.println("\nSending 'GET' request to URL : " + url);
 		System.out.println("Response Code : " + responseCode);

 		BufferedReader in = new BufferedReader(
 		        new InputStreamReader(con.getInputStream()));
 		String inputLine;
 		StringBuffer response = new StringBuffer();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		in.close();

 		//print result
 		System.out.println(response.toString());

 	}
 	
 	
 	private void sendG() throws Exception {
 		String url="https://api.enviopack.com/";
 		String cotizar="cotizar/costo?access_token="+this.token;
 		
 		
 		
 		
 		
 	}

 	// HTTP POST request
 	private void sendPost() throws Exception {

 		String url = "https://selfsolve.apple.com/wcResults.do";
 		URL obj = new URL(url);
 		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

 		//add reuqest header
 		con.setRequestMethod("POST");
 		con.setRequestProperty("User-Agent", USER_AGENT);
 		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

 		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

 		// Send post request
 		con.setDoOutput(true);
 		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
 		wr.writeBytes(urlParameters);
 		wr.flush();
 		wr.close();

 		int responseCode = con.getResponseCode();
 		System.out.println("\nSending 'POST' request to URL : " + url);
 		System.out.println("Post parameters : " + urlParameters);
 		System.out.println("Response Code : " + responseCode);

 		BufferedReader in = new BufferedReader(
 		        new InputStreamReader(con.getInputStream()));
 		String inputLine;
 		StringBuffer response = new StringBuffer();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		in.close();

 		//print result
 		System.out.println(response.toString());

 	}
    


}
