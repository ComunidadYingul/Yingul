package com.valework.yingul;

import java.util.HashMap;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.stereotype.Component;
import com.valework.yingul.util.MethodTypes;
import com.valework.yingul.util.VisaAPIClient;
import com.valework.yingul.util.Property;
import com.valework.yingul.util.VisaProperties;

@Component
public class VisaFunds {

	String apiKey;
    String paymentAuthorizationRequest;
    VisaAPIClient visaAPIClient;
    
	public CloseableHttpResponse authorizeCreditCard() throws Exception{
		this.visaAPIClient = new VisaAPIClient();
        this.apiKey = VisaProperties.getProperty(Property.API_KEY);
        this.paymentAuthorizationRequest = 
                "{\"amount\": \"1\","
                 + "\"currency\": \"USD\","
                 + "\"payment\": {"
                     + "\"cardNumber\": \"5555555555554444\","
                     + "\"cardExpirationMonth\": \"10\","
                     + "\"cardExpirationYear\": \"2020\""
                     + "}"
                 + "}";
        String baseUri = "cybersource/";
        String resourcePath = "payments/v1/authorizations";
        String queryString = "apikey=" + apiKey;
        CloseableHttpResponse response = this.visaAPIClient.doXPayTokenRequest(baseUri, resourcePath, queryString, "Payment Authorization Test", this.paymentAuthorizationRequest, MethodTypes.POST, new HashMap<String, String>());
        return response;
	}
	public CloseableHttpResponse captureFundsForAuthorize() throws Exception{
		this.visaAPIClient = new VisaAPIClient();
        this.apiKey = VisaProperties.getProperty(Property.API_KEY);
        this.paymentAuthorizationRequest = "\r\n" + 
        		"{\r\n" + 
        		"\"amount\": \"1\",\r\n" + 
        		"\"currency\": \"USD\",\r\n" + 
        		"\"purchasingLevel\": \"3\",\r\n" + 
        		"\"vcOrderId\": \"\",\r\n" + 
        		"\"referenceId\": \"124\",\r\n" + 
        		"\"billTo\": {\r\n" + 
        		"\"street1\": \"901 Metro Center Blvd\",\r\n" + 
        		"\"street2\": \"Folsom street\",\r\n" + 
        		"\"city\": \"Foster City\",\r\n" + 
        		"\"country\": \"USA\",\r\n" + 
        		"\"state\": \"CA\",\r\n" + 
        		"\"postalCode\": \"94404\",\r\n" + 
        		"\"firstName\": \"userFirst\",\r\n" + 
        		"\"lastName\": \"userLast\",\r\n" + 
        		"\"email\": \"bill@cybs.com\",\r\n" + 
        		"\"buildingNumber\": \"24\",\r\n" + 
        		"\"district\": \"san mateo\",\r\n" + 
        		"\"company\": \"visa\",\r\n" + 
        		"\"ipAddress\": \"10.20.408.500\",\r\n" + 
        		"\"phoneNumber\": \"6508764564\"\r\n" + 
        		"},\r\n" + 
        		"\"shipTo\": {\r\n" + 
        		"\"firstName\": \"userFirst\",\r\n" + 
        		"\"lastName\": \"userLast\",\r\n" + 
        		"\"street1\": \"901 Metro Center Blvd\",\r\n" + 
        		"\"street2\": \"Folsom st.\",\r\n" + 
        		"\"city\": \"Foster City\",\r\n" + 
        		"\"state\": \"CA\",\r\n" + 
        		"\"postalCode\": \"94404\",\r\n" + 
        		"\"fromPostalCode\": \"95135\",\r\n" + 
        		"\"country\": \"USA\",\r\n" + 
        		"\"phoneNumber\": \"6507686543\",\r\n" + 
        		"\"shippingMethod\": \"Mail\"\r\n" + 
        		"},\r\n" + 
        		"\"merchantDefinedData\": {\r\n" + 
        		"\"field1\": \"test1\",\r\n" + 
        		"\"field2\": \"test2\",\r\n" + 
        		"\"field3\": \"test3\",\r\n" + 
        		"\"field4\": \"test4\",\r\n" + 
        		"\"field5\": \"test5\",\r\n" + 
        		"\"field6\": \"test6\",\r\n" + 
        		"\"field7\": \"test7\",\r\n" + 
        		"\"field8\": \"test8\",\r\n" + 
        		"\"field9\": \"test9\",\r\n" + 
        		"\"field10\": \"test10\",\r\n" + 
        		"\"field11\": \"test11\",\r\n" + 
        		"\"field12\": \"test12\",\r\n" + 
        		"\"field13\": \"test13\",\r\n" + 
        		"\"field14\": \"test14\",\r\n" + 
        		"\"field15\": \"test15\",\r\n" + 
        		"\"field16\": \"test16\",\r\n" + 
        		"\"field17\": \"test17\",\r\n" + 
        		"\"field18\": \"test18\",\r\n" + 
        		"\"field19\": \"test19\",\r\n" + 
        		"\"field20\": \"test20\",\r\n" + 
        		"\"field21\": \"test21\",\r\n" + 
        		"\"field22\": \"test22\",\r\n" + 
        		"\"field23\": \"test23\",\r\n" + 
        		"\"field24\": \"test24\",\r\n" + 
        		"\"field25\": \"test25\",\r\n" + 
        		"\"field26\": \"test26\",\r\n" + 
        		"\"field27\": \"test27\",\r\n" + 
        		"\"field28\": \"test28\",\r\n" + 
        		"\"field29\": \"test29\",\r\n" + 
        		"\"field30\": \"test30\",\r\n" + 
        		"\"field31\": \"test31\",\r\n" + 
        		"\"field32\": \"test32\",\r\n" + 
        		"\"field33\": \"test33\",\r\n" + 
        		"\"field34\": \"test34\",\r\n" + 
        		"\"field35\": \"test35\",\r\n" + 
        		"\"field36\": \"test36\",\r\n" + 
        		"\"field37\": \"test37\",\r\n" + 
        		"\"field38\": \"test38\",\r\n" + 
        		"\"field39\": \"test39\",\r\n" + 
        		"\"field40\": \"test40\",\r\n" + 
        		"\"field41\": \"test41\",\r\n" + 
        		"\"field42\": \"test42\",\r\n" + 
        		"\"field43\": \"test43\",\r\n" + 
        		"\"field44\": \"test44\",\r\n" + 
        		"\"field45\": \"test45\",\r\n" + 
        		"\"field46\": \"test46\",\r\n" + 
        		"\"field47\": \"test47\",\r\n" + 
        		"\"field48\": \"test48\",\r\n" + 
        		"\"field49\": \"test49\",\r\n" + 
        		"\"field50\": \"test50\",\r\n" + 
        		"\"field51\": \"test51\",\r\n" + 
        		"\"field52\": \"test52\",\r\n" + 
        		"\"field53\": \"test53\",\r\n" + 
        		"\"field54\": \"test54\",\r\n" + 
        		"\"field55\": \"test55\",\r\n" + 
        		"\"field56\": \"test56\",\r\n" + 
        		"\"field57\": \"test57\",\r\n" + 
        		"\"field58\": \"test58\",\r\n" + 
        		"\"field59\": \"test59\",\r\n" + 
        		"\"field60\": \"test60\",\r\n" + 
        		"\"field61\": \"test51\",\r\n" + 
        		"\"field62\": \"test52\",\r\n" + 
        		"\"field63\": \"test53\",\r\n" + 
        		"\"field64\": \"test54\",\r\n" + 
        		"\"field65\": \"test55\",\r\n" + 
        		"\"field66\": \"test56\",\r\n" + 
        		"\"field67\": \"test57\",\r\n" + 
        		"\"field68\": \"test58\",\r\n" + 
        		"\"field69\": \"test59\",\r\n" + 
        		"\"field70\": \"test60\",\r\n" + 
        		"\"field71\": \"test51\",\r\n" + 
        		"\"field72\": \"test52\",\r\n" + 
        		"\"field73\": \"test53\",\r\n" + 
        		"\"field74\": \"test54\",\r\n" + 
        		"\"field75\": \"test55\",\r\n" + 
        		"\"field76\": \"test56\",\r\n" + 
        		"\"field77\": \"test57\",\r\n" + 
        		"\"field78\": \"test58\",\r\n" + 
        		"\"field79\": \"test59\",\r\n" + 
        		"\"field80\": \"test60\",\r\n" + 
        		"\"field81\": \"test51\",\r\n" + 
        		"\"field82\": \"test52\",\r\n" + 
        		"\"field83\": \"test53\",\r\n" + 
        		"\"field84\": \"test54\",\r\n" + 
        		"\"field85\": \"test55\",\r\n" + 
        		"\"field86\": \"test56\",\r\n" + 
        		"\"field87\": \"test57\",\r\n" + 
        		"\"field88\": \"test58\",\r\n" + 
        		"\"field89\": \"test59\",\r\n" + 
        		"\"field90\": \"test60\",\r\n" + 
        		"\"field91\": \"test51\",\r\n" + 
        		"\"field92\": \"test52\",\r\n" + 
        		"\"field93\": \"test53\",\r\n" + 
        		"\"field94\": \"test54\",\r\n" + 
        		"\"field95\": \"test55\",\r\n" + 
        		"\"field96\": \"test56\",\r\n" + 
        		"\"field97\": \"test57\",\r\n" + 
        		"\"field98\": \"test58\",\r\n" + 
        		"\"field99\": \"test59\",\r\n" + 
        		"\"field100\": \"test60\"\r\n" + 
        		"},\r\n" + 
        		"\"items\": [\r\n" + 
        		"{\r\n" + 
        		"\"productSKU\": \"A100\",\r\n" + 
        		"\"productCategoryCode\": \"electronics\",\r\n" + 
        		"\"quantity\": \"1\",\r\n" + 
        		"\"amount\": \"12.99\",\r\n" + 
        		"\"taxAmount\": \"0.80\",\r\n" + 
        		"\"productName\": \"Test1\",\r\n" + 
        		"\"commodityCode\": \"123456789\",\r\n" + 
        		"\"discountIndicator\": \"Y\",\r\n" + 
        		"\"discountRate\": \"0.05\",\r\n" + 
        		"\"discountAmount\": \"1.45\",\r\n" + 
        		"\"discountManagementIndicator\": \"1\",\r\n" + 
        		"\"vatRate\": \"1\",\r\n" + 
        		"\"typeOfSupply\": \"00\",\r\n" + 
        		"\"unitOfMeasure\": \"meter\",\r\n" + 
        		"\"totalAmount\": \"5.00\",\r\n" + 
        		"\"taxRate\": \"0.10\",\r\n" + 
        		"\"taxTypeApplied\": \"Cntry\",\r\n" + 
        		"\"taxStatusIndicator\": \"1\",\r\n" + 
        		"\"invoiceNumber\": \"123\",\r\n" + 
        		"\"grossNetIndicator\": \"N\",\r\n" + 
        		"\"nationalTax\": \"0.10\",\r\n" + 
        		"\"localTax\": \"1.53\",\r\n" + 
        		"\"alternateTaxId\": \"98765432198765\",\r\n" + 
        		"\"alternateTaxAmount\": \"1.00\",\r\n" + 
        		"\"alternateTaxRate\": \"1.50\",\r\n" + 
        		"\"alternateTaxTypeApplied\": \"VAT\",\r\n" + 
        		"\"alternateTaxTypeIdentifier\": \"10\"\r\n" + 
        		"},\r\n" + 
        		"{\r\n" + 
        		"\"productSKU\": \"A101\",\r\n" + 
        		"\"productCategoryCode\": \"clothing\",\r\n" + 
        		"\"quantity\": \"4\",\r\n" + 
        		"\"amount\": \"2.99\",\r\n" + 
        		"\"taxAmount\": \"0.80\",\r\n" + 
        		"\"productName\": \"Test2\",\r\n" + 
        		"\"commodityCode\": \"123456780\",\r\n" + 
        		"\"discountIndicator\": \"Y\",\r\n" + 
        		"\"discountRate\": \"0.05\",\r\n" + 
        		"\"discountAmount\": \"1.45\",\r\n" + 
        		"\"discountManagementIndicator\": \"1\",\r\n" + 
        		"\"vatRate\": \"1\",\r\n" + 
        		"\"typeOfSupply\": \"00\",\r\n" + 
        		"\"unitOfMeasure\": \"meter\",\r\n" + 
        		"\"totalAmount\": \"10.00\",\r\n" + 
        		"\"taxRate\": \"0.10\",\r\n" + 
        		"\"taxTypeApplied\": \"Cntry\",\r\n" + 
        		"\"taxStatusIndicator\": \"1\",\r\n" + 
        		"\"invoiceNumber\": \"123\",\r\n" + 
        		"\"grossNetIndicator\": \"N\",\r\n" + 
        		"\"nationalTax\": \"0.10\",\r\n" + 
        		"\"localTax\": \"1.53\",\r\n" + 
        		"\"alternateTaxId\": \"98765432198760\",\r\n" + 
        		"\"alternateTaxAmount\": \"1.00\",\r\n" + 
        		"\"alternateTaxRate\": \"1.50\",\r\n" + 
        		"\"alternateTaxTypeApplied\": \"VAT\",\r\n" + 
        		"\"alternateTaxTypeIdentifier\": \"10\"\r\n" + 
        		"}\r\n" + 
        		"],\r\n" + 
        		"\"merchantDescriptor\": {\r\n" + 
        		"\"primary\": \"iufkdjjjdsccsdkjc\",\r\n" + 
        		"\"alternate\": \"kdlkfklsklsk\",\r\n" + 
        		"\"city\": \"san Mateo\",\r\n" + 
        		"\"contact\": \"89787889887\",\r\n" + 
        		"\"country\": \"USA\",\r\n" + 
        		"\"postalCode\": \"94404\",\r\n" + 
        		"\"state\": \"CA\",\r\n" + 
        		"\"street\": \"shellBlvd\"\r\n" + 
        		"},\r\n" + 
        		"\"order\": {\r\n" + 
        		"\"amexDataTAA1\": \"Sporting Goods\",\r\n" + 
        		"\"amexDataTAA2\": \"Jewelry\",\r\n" + 
        		"\"amexDataTAA3\": \"Accessories\",\r\n" + 
        		"\"amexDataTAA4\": \"Electronics\",\r\n" + 
        		"\"authorizedContactName\": \"Test\",\r\n" + 
        		"\"cardAcceptorRefNumber\": \"123\",\r\n" + 
        		"\"alternateTaxAmount\": \"35.50\",\r\n" + 
        		"\"alternateTaxId\": \"12\",\r\n" + 
        		"\"alternateTaxAmountIndicator\": \"1\",\r\n" + 
        		"\"dutyAmount\": \"0.10\",\r\n" + 
        		"\"dutyAmountSign\": \"positive\",\r\n" + 
        		"\"freightAmount\": \"0.98\",\r\n" + 
        		"\"freightAmountSign\": \"positive\",\r\n" + 
        		"\"localTax\": \"1.53\",\r\n" + 
        		"\"localTaxIndicator\": \"1\",\r\n" + 
        		"\"nationalTax\": \"2.99\",\r\n" + 
        		"\"nationalTaxIndicator\": \"1\",\r\n" + 
        		"\"merchantVATRegistrationNumber\": \"AB12C3nomorethan20ch\",\r\n" + 
        		"\"orderDiscountAmount\": \"20.00\",\r\n" + 
        		"\"orderDiscountAmountSign\": \"positive\",\r\n" + 
        		"\"orderDiscountManagementIndicator\": \"1\",\r\n" + 
        		"\"purchaserCode\": \"testCode\",\r\n" + 
        		"\"purchaserOrderDate\": \"160913\",\r\n" + 
        		"\"purchaserVATRegistrationNumber\": \"98ZXW7Q554321\",\r\n" + 
        		"\"summaryCommodityCode\": \"A2Zs\",\r\n" + 
        		"\"taxIndicator\": \"Y\",\r\n" + 
        		"\"taxManagementIndicator\": \"0\",\r\n" + 
        		"\"totalTaxTypeCode\": \"056\",\r\n" + 
        		"\"vatInvoiceRefNumber\": \"vivatrefnotest1\",\r\n" + 
        		"\"vatTaxAmount\": \"35.50\",\r\n" + 
        		"\"vatTaxAmountSign\": \"positive\",\r\n" + 
        		"\"vatTaxRate\": \"0.12\"\r\n" + 
        		"}\r\n" + 
        		"}";
        String baseUri = "cybersource/";
        String resourcePath = "payments/v1/sales";
        String queryString = "apikey=" + apiKey;
        CloseableHttpResponse response = this.visaAPIClient.doXPayTokenRequest(baseUri, resourcePath, queryString, "Payment Authorization Test", this.paymentAuthorizationRequest, MethodTypes.POST, new HashMap<String, String>());
        return response;
	}
	public CloseableHttpResponse salesTransaction(Long cardnumber,double amount,int duemonth, int dueyear) throws Exception{
		//
		String prem="";
		if(duemonth<10) {
			prem="0";
		}
		//
		this.visaAPIClient = new VisaAPIClient();
        this.apiKey = VisaProperties.getProperty(Property.API_KEY);
        this.paymentAuthorizationRequest = 
                "{\"amount\": \""+amount+"\","
                 + "\"currency\": \"USD\","
                 + "\"payment\": {"
                     + "\"cardNumber\": \""+cardnumber+"\","
                     + "\"cardExpirationMonth\": \""+prem+duemonth+"\","
                     + "\"cardExpirationYear\": \"20"+dueyear+"\""
                     + "}"
                 + "}";
        String baseUri = "cybersource/";
        String resourcePath = "payments/v1/sales";
        String queryString = "apikey=" + apiKey;
        CloseableHttpResponse response = this.visaAPIClient.doXPayTokenRequest(baseUri, resourcePath, queryString, "Payment Authorization Test", this.paymentAuthorizationRequest, MethodTypes.POST, new HashMap<String, String>());
        return response;
	}

}
