package com.valework.yingul.logistic;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class http {
	
	public String request(PropertyObjectHttp objectHttp) throws IOException {
		//=new PropertyObjectHttp();
		//PropertyHttp pro=new PropertyHttp();
		//objectHttp=pro.Fedex();
		//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		URL url = new URL(objectHttp.getUrl());
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = objectHttp.getBody();
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		httpConn.setRequestProperty("Content-Length",String.valueOf(b.length));
		List<RequestPropertyHeders> requestProperty=objectHttp.getRequestProperty();
		for (RequestPropertyHeders requestPropertyHeders : requestProperty) {
			System.out.println("getName:"+requestPropertyHeders.getName()+","+requestPropertyHeders.getValue());
			httpConn.setRequestProperty(requestPropertyHeders.getName(),requestPropertyHeders.getValue());
		}
		httpConn.setRequestMethod(objectHttp.getRequestMethod());
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();
		//Write the content of the request to the outputstream of the HTTP Connection.
		out.write(b);
		out.close();
		//Ready with sending the request.
		//Read the response.
		InputStreamReader isr = null;
		if (httpConn.getResponseCode() == 200) {
		  isr = new InputStreamReader(httpConn.getInputStream());
		} else {
		  isr = new InputStreamReader(httpConn.getErrorStream());
		}
		BufferedReader in = new BufferedReader(isr);
		//Write the SOAP message response to a String.
		//int a=0;
		while ((responseString = in.readLine()) != null) {
			outputString = outputString + responseString;
		}
		System.out.println("outputStringfedex:"+outputString);
		return ""+outputString;
	}

}
