package com.valework.yingul.logistic;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.valework.yingul.model.Yng_Branch;
import com.valework.yingul.model.Yng_Quote;
import com.valework.yingul.model.Yng_Shipment;

public class FedexResponce {
	public Yng_Branch fedexBranch(String xml) {
		//Xml xml=new Xml();
		Yng_Branch bra=new Yng_Branch();
		String outputString =xml;
		try {
			
			//parserString(outputString);TotalNetFedExCharge
			JSONObject xmlJSONObj = XML.toJSONObject(outputString);
			
			System.out.println("xmlJSONObj:"+xmlJSONObj);
			//StringJsonSerch js=new  StringJsonSerch();
			String res=StringJsonSerch.recurseKeys(xmlJSONObj, "AddressToLocationRelationships");
			String MatchedAddress=StringJsonSerch.recurseKeys(xmlJSONObj, "MatchedAddress");
			System.out.println("res:"+res);
			String City ="";
			String PostalCode="";
			String StreetLines ="";
			
			if(res.length()>7) {
				City=StringJsonSerch.stringJsonSearch(MatchedAddress, "City");
				StreetLines=StringJsonSerch.stringJsonSearch(MatchedAddress, "StreetLines");
				PostalCode=StringJsonSerch.stringJsonSearch(MatchedAddress, "PostalCode");
			// precio=StringJsonSerch.stringJsonSearch(res, "Amount");
			}
			bra.setDateDelivery("");
			bra.setLocation(City);
			bra.setNameMail("fedex");
			bra.setRespuesta(""+xmlJSONObj);
			bra.setSchedules("De Lunes a Viernes de 09:00 a 18:00 - Sabados de 09:00 a 16:00");
			bra.setStreet(""+StreetLines+" ,"+PostalCode+","+City);
			//String s="{\"\":\"\"}";
			//System.out.println("City:"+City);
			//System.out.println("precio:"+precio);
			//System.out.println("res:"+res);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bra;	
	}

	public Yng_Quote fedexQuote(String xml) throws JSONException {
		String outputString =xml;
		Yng_Quote quote =new Yng_Quote();
		double rate = 0;
		double rateOrigin = 0;
		String respuesta = xml;
		JSONObject xmlJSONObj = XML.toJSONObject(outputString);
		
		System.out.println("xmlJSONObj:"+xmlJSONObj);
	
		String res=StringJsonSerch.recurseKeys(xmlJSONObj, "RateReplyDetails");
		System.out.println("res:"+res);
		String resRR=StringJsonSerch.arrayJson(res, "TotalNetFedExCharge");
		System.out.println("resRR:"+resRR);
		String Amount=StringJsonSerch.stringJsonSearch(resRR, "Amount");
		System.out.println("Amount:"+Amount);	
		rateOrigin=Double.parseDouble(Amount);
		quote.setRate(rate);		
		quote.setRateOrigin(rateOrigin);		
		quote.setRespuesta(respuesta);
		return quote;
	}
	
	public Yng_Shipment fedexShipment(String xml) {
		Yng_Shipment shipm =new Yng_Shipment();
		//String shipmentCod = "";
		String ticket = "";
		//String typeMail = "";
		String outputString =xml;
		try {
			String res=StringJsonSerch.stringJsonSearch(outputString, "CompletedShipmentDetail");
			System.out.println("res:"+res);			
			String res2=StringJsonSerch.stringJsonSearch(res, "CompletedPackageDetails");
			String Label=StringJsonSerch.stringJsonSearch(res2, "Label");
			String Image=StringJsonSerch.stringJsonSearch(Label, "Image");
			System.out.println("Image:"+Image);
			String MasterTrackingId=StringJsonSerch.stringJsonSearch(res, "MasterTrackingId");
			System.out.println("MasterTrackingId:"+MasterTrackingId);
			
			
			shipm.setRespuesta(xml);			
			shipm.setShipmentCod(MasterTrackingId);
			shipm.setTicket(ticket);			
			JSONObject xmlJSONObj = XML.toJSONObject(outputString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return shipm;
	}
}
