package com.valework.yingul.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.logistic.AndreaniXML;
import com.valework.yingul.logistic.PropertyObjectHttp;
import com.valework.yingul.logistic.http;
import com.valework.yingul.model.Yng_Quote;

@RestController
@RequestMapping("/shipping")
public class ShippingController {
	
	@RequestMapping(value = "/quote", method = RequestMethod.POST)
  	@ResponseBody
	  public List<Yng_Quote> quote(@Valid @RequestBody  Yng_Quote quo){
		  List<Yng_Quote> quotesList=new ArrayList<Yng_Quote>();
		  Yng_Quote quote=new Yng_Quote();
		  
		  
		  
		return null;  
	  }
	  
	/*  public void branch() {
			PropertyObjectHttp objectHttp= new PropertyObjectHttp();
			
			String url="https://sucursales.andreani.com/ws?wsdl";
			objectHttp.setUrl(url);
			objectHttp.setRequestMethod(objectHttp.POST);
			String body;
			AndreaniXML andreaniXML=new AndreaniXML(); 
			body=andreaniXML.AndreaniLocation(quo, postalCode);
			objectHttp.setBody(body);
			http  request=new http();
			try {
				String bb=request.request(objectHttp);
				System.out.println("bb:"+bb);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

}
