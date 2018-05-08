package com.valework.yingul.logistic;

import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Product;
import com.valework.yingul.model.Yng_Quote;
import com.valework.yingul.model.Yng_Shipping;

public class AndreaniXML {
	  String UserNameAndreani;
	  String PasswordAndreni;
	  String ContratoAndreni;
	  String ClienteAndreni;
	public AndreaniXML() {
	}
	public AndreaniXML(String userNameAndreani, String passwordAndreni, String contratoAndreni, String clienteAndreni) {
		UserNameAndreani = userNameAndreani;
		PasswordAndreni = passwordAndreni;
		ContratoAndreni = contratoAndreni;
		ClienteAndreni = clienteAndreni;
	}
	
	public String AndreaniLocation(Yng_Quote quo,String postalCode) {
    	return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<env:Envelope\r\n" + 
				"    xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"\r\n" + 
				"    xmlns:ns1=\"urn:ConsultarSucursales\"\r\n" + 
				"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
				"    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\r\n" + 
				"    xmlns:ns2=\"http://xml.apache.org/xml-soap\"\r\n" + 
				"    xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\r\n" + 
				"    xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
				"    <env:Header>\r\n" + 
				"        <ns3:Security env:mustUnderstand=\"true\">\r\n" + 
				"            <ns3:UsernameToken>\r\n" + 
				"                <ns3:Username>"
				+ UserNameAndreani
				+ "</ns3:Username>\r\n" + 
				"                <ns3:Password>"
				+ PasswordAndreni
				+ "</ns3:Password>\r\n" + 
				"            </ns3:UsernameToken>\r\n" + 
				"        </ns3:Security>\r\n" + 
				"    </env:Header>\r\n" + 
				"    <env:Body>\r\n" + 
				"        <ns1:ConsultarSucursales env:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
				"            <Consulta xsi:type=\"ns2:Map\">\r\n" + 
				"                <item>\r\n" + 
				"                    <key xsi:type=\"xsd:string\">consulta</key>\r\n" + 
				"                    <value xsi:type=\"ns2:Map\">\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Localidad</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				//+ quo.getYng_User().getYng_Ubication().getYng_City().getName()
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">CodigoPostal</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ postalCode
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Provincia</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				//+ quo.getYng_User().getYng_Ubication().getYng_Province().getName()
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                    </value>\r\n" + 
				"                </item>\r\n" + 
				"            </Consulta>\r\n" + 
				"        </ns1:ConsultarSucursales>\r\n" + 
				"    </env:Body>\r\n" + 
				"</env:Envelope>";
	}

	public String AndreaniRate(Yng_Quote quo, Yng_Product getProductByIdItem) {
		return "";
	}
	
	public String AndreaniShipping(Yng_Person per,Yng_Shipping shi,Yng_Person perItem,Yng_Product pro) {
		return "";
	}
}
