package com.valework.yingul.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;



import javax.mail.MessagingException;
import javax.net.ssl.HttpsURLConnection;
import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.IOException;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.valework.yingul.model.AndreaniCot;
import com.valework.yingul.model.Yng_AndreaniCotizacion;
import com.valework.yingul.model.Yng_AndreaniSucursal;
import com.valework.yingul.model.Yng_Branch;
import com.valework.yingul.model.Yng_Cotizacion;
import com.valework.yingul.model.Yng_Cotizar;
import com.valework.yingul.model.Yng_Envio;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_Product;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.model.Yng_Quote;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_Token;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.MotorizedService;
import com.valework.yingul.service.ProductService;
import com.valework.yingul.service.PropertyService;
import com.valework.yingul.service.ServiceService;
import com.valework.yingul.service.StandardService;

import andreaniapis.*;
import ch.qos.logback.core.net.SyslogOutputStream;

import com.valework.yingul.dao.CotizacionDao;
import com.valework.yingul.dao.EnvioDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.logistic.*;





@RestController
@RequestMapping("/logistics")
public class LogisticsController {
	private  String FedEXAuthenticationKey ;
	private  String FedExMeterNumber;
	private  String FedExAccountNumber;
	private  String FedexPassword;
	private String urlCotizar="https://cotizadorpreprod.andreani.com/ws?wsdl";
	private String urlComprar="https://integracionestest.andreani.com:4000/E-ImposicionRemota?wsdl";
	private String urlSuc="https://sucursalespreprod.andreani.com/ws?wsdl";
	
	
	
	private final String apiKey="46e903f2a3e1cb1933db836b99df089f91e9fd57";
	private final String secretKey="8a5b791c1e426a96650b54693f04aec02e89b280";
	private final String urlApi= "https://api.enviopack.com";
	private final String cotizarDomicilio="/cotizar/precio/a-domicilio?access_token=";
	private final String cotizarSucursal="/cotizar/precio/a-sucursal?access_token=";
	private final String cotizarCosto="/cotizar/costo?access_token=";
	Yng_Standard standard;//= new Yng_Standard();
	
	private final String auth="/auth";
	private String TOKEN;
    private String TYPE="application/x-www-form-urlencoded";
    private final String USER_AGENT = "Mozilla/5.0";
    private Yng_Cotizar yngCotizar= new Yng_Cotizar();
	@Autowired
	UserDao userDao;
	@Autowired
	StandardService standardService;
	@Autowired
	ItemDao itemDao;
	@Autowired
	ServiceService serviceService;
	@Autowired
	ProductService productService;
	@Autowired
	MotorizedService motorizedService;
	@Autowired
	PropertyService propertyService;
	
    @RequestMapping("/token")
    private String token() {
    	String da="guardado";
    	LogisticsController http = new LogisticsController();

		
		try {
				http.getAccessToken();
			} catch (Exception e) {

			e.printStackTrace();
		}
 	
    	return da;
    }
    
    private void cotizarDomicilio()throws Exception{
    	getAccessToken();
    	String urlDomiciliio="https://api.enviopack.com/cotizar/precio/a-domicilio?access_token="+TOKEN+"&provincia=C&codigo_postal=1414&peso=1.5";
    	urlDomiciliio=this.urlApi+this.cotizarDomicilio+this.TOKEN+"&provincia=C&codigo_postal=1414&peso=1.5";

 		URL obj = new URL(urlDomiciliio);
 		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 		con.setRequestMethod("GET");
 		con.setRequestProperty("User-Agent", USER_AGENT);
 		int responseCode = con.getResponseCode();
 		System.out.println("Response Code : " + responseCode);
 		BufferedReader in = new BufferedReader(
 		           new InputStreamReader(con.getInputStream()));
 		String inputLine;
 		StringBuffer response = new StringBuffer();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		in.close();
 		System.out.println(response.toString());
    	
    }
    
    @RequestMapping("/cotizar")
     private String cotizar() {
    	try {
    		
			cotizarDomicilio();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	 return "save";
    }
    
    private void getAccessToken() throws Exception{

    	URL obj=new URL(urlApi+auth);
    	HttpURLConnection con=(HttpURLConnection) obj.openConnection();
    	con.setRequestMethod("POST");
    	con.setRequestProperty("User-Agent", USER_AGENT);
    	con.setRequestProperty("Content-Type", TYPE);
    	String urlParameters = "api-key"+"="+apiKey+"&secret-key="+secretKey;
    	con.setDoOutput(true);
    	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
 		wr.writeBytes(urlParameters);
 		wr.flush();
 		wr.close();
    	
    	int responseCode=con.getResponseCode();

 		System.out.println("Response Code : " + responseCode);

    	BufferedReader in =new BufferedReader(new InputStreamReader(con.getInputStream()));
    	
    	String inputLine;
    	StringBuffer response=new StringBuffer();
    	while((inputLine = in.readLine())!=null) {
    		response.append(inputLine);
    		
    	}
    	in.close();    	
    	System.out.println(response.toString());
    	System.out.println("");
    	jsonToken(response.toString());
    }
    
    
 	
 	
 	
 	
 	private void jsonToken( String json) {
         ObjectMapper mapper = new ObjectMapper();
         Yng_Token token;
		try {
			token = mapper.readValue(json, Yng_Token.class);
			// System.out.println(token.toString());
			 System.out.println("token:"+token.getToken());
			 TOKEN=token.getToken();
			 
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
 	
 	@RequestMapping(value = "/cotizarItem", method = RequestMethod.POST)
	@ResponseBody
    public String cotizarPost(@Valid @RequestBody Yng_Cotizar cotizar) throws MessagingException {
 		Yng_Cotizar cotizarTemp=cotizar;
 		try {
			cotizarDomicilio1(cotizarTemp);
		} catch (Exception e) {
						e.printStackTrace();
		}
 		List<Yng_Cotizar>cotizarList;
 		
 		return "save";
 	}
 	
 	private void cotizarDomicilio1(Yng_Cotizar cotizar)throws Exception{
    	getAccessToken();
    	String urlDomiciliio="https://api.enviopack.com/cotizar/precio/a-domicilio?access_token="+TOKEN+"&provincia=C&codigo_postal=1414&peso=1.5";
    	urlDomiciliio=this.urlApi+this.cotizarSucursal+this.TOKEN+"&provincia="+cotizar.getProvincia()+"&codigo_postal="+cotizar.getCodigo_postal()+"&peso="+cotizar.getPeso();

 		URL obj = new URL(urlDomiciliio);
 		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 		con.setRequestMethod("GET");
 		con.setRequestProperty("User-Agent", USER_AGENT);
 		int responseCode = con.getResponseCode();
 		System.out.println("Response Code : " + responseCode);
 		BufferedReader in = new BufferedReader(
 		           new InputStreamReader(con.getInputStream()));
 		String inputLine;
 		StringBuffer response = new StringBuffer();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		in.close();
 		System.out.println(response.toString());
    	
    }
 	
 	
 	@RequestMapping(value = "/cotizarItemA", method = RequestMethod.POST)
	@ResponseBody
    public String andreaniCotizar(@Valid @RequestBody Yng_Cotizar cotizar) throws MessagingException {
 		Yng_Cotizar cotizarTemp=cotizar;
 		System.out.println(cotizarTemp.toString());
 		
 		try {
 			andreaniPost();
		} catch (Exception e) {
						e.printStackTrace();
		}
 		List<Yng_Cotizar>cotizarList;
 		String ta="";
 		try {
			ta=andreaniCotizacion(cotizarTemp.getCodigo_postal(),cotizarTemp.getCodAndreani(),cotizarTemp.getPeso(),cotizarTemp.getVolumen(),"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		System.out.println("tarifa:"+ta);
 		return ""+ta;
 	}
 	
 	@RequestMapping(value = "/cotizarItemB", method = RequestMethod.POST)
	@ResponseBody
    public String andreaniPost(@Valid @RequestBody Yng_Cotizar cotizar) throws MessagingException {
 		Yng_Cotizar cotizarTemp=cotizar;
 		try {
 			andreaniPost();
		} catch (Exception e) {
						e.printStackTrace();
		}
 		List<Yng_Cotizar>cotizarList;
 		
 		return "save";
 	}
 	
    private void andreaniPost() throws Exception{
    	
    	System.out.println("póst");
		String body="<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"xmlns:ns1=\"urn:ConsultarSucursales\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\"> <env:Header><ns3:Security env:mustUnderstand=\"true\"><ns3:UsernameToken> <ns3:Username>STAGING_WS</ns3:Username><ns3:Password>ANDREANI</ns3:Password> </ns3:UsernameToken></ns3:Security></env:Header><env:Body><ns1:ConsultarSucursales env:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\"><Consulta xsi:type=\"ns2:Map\"><item><key xsi:type=\"xsd:string\">consulta</key><value xsi:type=\"ns2:Map\"> <item><key xsi:type=\"xsd:string\">Localidad</key><value xsi:type=\"xsd:string\"></value></item><item><key xsi:type=\"xsd:string\">"
		+ "CodigoPostal</key><value xsi:type=\"xsd:string\">"
		+ "</value></item><item><key xsi:type=\"xsd:string\">Provincia</key><value xsi:type=\"xsd:string\"></value></item></value></item></Consulta></ns1:ConsultarSucursales></env:Body></env:Envelope>";
    	
		StringEntity stringEntity = new StringEntity(body, "UTF-8");
		
        stringEntity.setChunked(true);

        // Request parameters and other properties.
        HttpPost httpPost = new HttpPost("https://sucursalespreprod.andreani.com/ws?wsdl");
        httpPost.setEntity(stringEntity);
        //httpPost.addHeader("Accept", "text/xml");
        //request.setHeader("Content-Type","application/xml;charset=UTF-8");
        //httpPost.setHeader("Content-Type","application/xml;charset=UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("SOAPAction", "soapAction");

        // Execute and get the response.
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();

        String strResponse = null;
        if (entity != null) {
            strResponse = EntityUtils.toString(entity);
            
        }
      //System.out.println("post  " +strResponse);
        System.out.println("uno"+strResponse);
       
 
     }
    
    
   
    
    
     private Document document;
     public void generadorDom() throws ParserConfigurationException {
    	 //document
    	 DocumentBuilderFactory factoria=DocumentBuilderFactory.newInstance();
    	 DocumentBuilder builder=factoria.newDocumentBuilder();
    	 document =builder.newDocument();
    	     	 
    	 
     }
     public void generarDocument() {
    	 Element productos=document.createElement("productos");
    	 document.appendChild(productos);
    	 
    	 Element producto=document.createElement("producto");
    	 productos.appendChild(producto);
    	 producto.setAttribute("codigo", "1");
    	 
    	 Element nombre=document.createElement("nombre");
    	 nombre.appendChild(document.createTextNode("Teclado"));
    	 producto.appendChild(nombre);
    	 
     }
     
     public void generarXml() throws IOException, TransformerException {
    	 TransformerFactory factoria =TransformerFactory.newInstance();
    	 Transformer transformer=factoria.newTransformer();
    	 Source sourse = new DOMSource(document);
    	 File file= new File("productos.xml");
    	 FileWriter fw=new FileWriter(file);
    	 PrintWriter pw= new PrintWriter(fw);
    	 Result result =new StreamResult(pw);
    	 
    	 transformer.transform(sourse, result);
    	 
     }
     String CodigoPostal="";
     String Localidad="";
     String Provincia="";
     
     @RequestMapping("/test")
     private String testP() throws Exception {
    	 
  		try {
  			this.CodigoPostal="";
  			this.Localidad="";
  			this.Provincia="";
  			andreaniSucursales(this.CodigoPostal,this.Localidad,this.Provincia);
		} catch (Exception e) {
						e.printStackTrace();
		}
   	
    	 return "save";
    }
     

     public String andreaniSucursales(String CodigoPostal,String Localidad,String Provincia) throws Exception{ 
    	 String body="<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"xmlns:ns1=\"urn:ConsultarSucursales\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\"> <env:Header><ns3:Security env:mustUnderstand=\"true\"><ns3:UsernameToken> <ns3:Username>STAGING_WS</ns3:Username><ns3:Password>ANDREANI</ns3:Password> </ns3:UsernameToken></ns3:Security></env:Header><env:Body><ns1:ConsultarSucursales env:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\"><Consulta xsi:type=\"ns2:Map\"><item><key xsi:type=\"xsd:string\">consulta</key><value xsi:type=\"ns2:Map\"> <item><key xsi:type=\"xsd:string\">Localidad</key><value xsi:type=\"xsd:string\"></value></item><item><key xsi:type=\"xsd:string\">"
		+ "CodigoPostal</key><value xsi:type=\"xsd:string\">"+CodigoPostal+ "</value></item><item><key xsi:type=\"xsd:string\">Provincia</key><value xsi:type=\"xsd:string\"></value></item></value></item></Consulta></ns1:ConsultarSucursales></env:Body></env:Envelope>";
    	
		String body3="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
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
				"                <ns3:Username></ns3:Username>\r\n" + 
				"                <ns3:Password></ns3:Password>\r\n" + 
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
				+ Localidad
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">CodigoPostal</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ CodigoPostal
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Provincia</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ Provincia
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                    </value>\r\n" + 
				"                </item>\r\n" + 
				"            </Consulta>\r\n" + 
				"        </ns1:ConsultarSucursales>\r\n" + 
				"    </env:Body>\r\n" + 
				"</env:Envelope>";
		String body2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
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
				"                <ns3:Username></ns3:Username>\r\n" + 
				"                <ns3:Password></ns3:Password>\r\n" + 
				"            </ns3:UsernameToken>\r\n" + 
				"        </ns3:Security>\r\n" + 
				"    </env:Header>\r\n" + 
				"    <env:Body>\r\n" +""+
				
				"    </env:Body>\r\n" + 
				"</env:Envelope>";

    	 
    	 StringEntity stringEntity = new StringEntity(body3, "UTF-8");
		
        stringEntity.setChunked(true);
        HttpPost httpPost = new HttpPost(urlSuc);
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("SOAPAction", "soapAction");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
        SAXParser sAXParser=saxParseFactory.newSAXParser();

        String numero="";
        String strResponse = null;
        if (entity != null) {
            strResponse = EntityUtils.toString(entity);
            SucursalHandler handlerS=new SucursalHandler();
            sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerS);
            ArrayList<ResultadoConsultarSucursales> sucursaleses=handlerS.getResultadoSucursales();
            for (ResultadoConsultarSucursales versione : sucursaleses) {
            	numero=versione.getNumero();
                System.out.println("versione.getNumero:"+versione.getNumero());
            
            }
        }
        
        System.out.println("strResponse:"+convertiraISO(strResponse));
        return ""+numero;

     }
     
     
     public String andreaniCotizacion(String CodigoPostal,String SucursalRetiro,String Peso,String Volumen,String ValorDeclarado) throws Exception{ 
    	 	String body2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<env:Envelope\r\n" + 
				"    xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"\r\n" + 
				"    xmlns:ns1=\"urn:CotizarEnvio\"\r\n" + 
				"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
				"    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\r\n" + 
				"    xmlns:ns2=\"http://xml.apache.org/xml-soap\"\r\n" + 
				"    xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\r\n" + 
				"    xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
				"    <env:Header>\r\n" + 
				"        <ns3:Security env:mustUnderstand=\"true\">\r\n" + 
				"            <ns3:UsernameToken>\r\n" + 
				"                <ns3:Username>STAGING_WS</ns3:Username>\r\n" + 
				"                <ns3:Password>ANDREANI</ns3:Password>\r\n" + 
				"            </ns3:UsernameToken>\r\n" + 
				"        </ns3:Security>\r\n" + 
				"    </env:Header>\r\n" + 
				"    <env:Body>\r\n" + 
				"        <ns1:CotizarEnvio env:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
				"            <cotizacionEnvio xsi:type=\"ns2:Map\">\r\n" + 
				"                <item>\r\n" + 
				"                    <key xsi:type=\"xsd:string\">cotizacionEnvio</key>\r\n" + 
				"                    <value xsi:type=\"ns2:Map\">\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">CPDestino</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ CodigoPostal
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Cliente</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">CL0003750</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Contrato</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">400006711</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Peso</key>\r\n" + 
				"                            <value xsi:type=\"xsd:int\">"
				+ Peso
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">SucursalRetiro</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ SucursalRetiro
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Volumen</key>\r\n" + 
				"                            <value xsi:type=\"xsd:int\">"
				+ Volumen
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">ValorDeclarado</key>\r\n" + 
				"                            <value xsi:type=\"xsd:int\">"
				+ ValorDeclarado
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                    </value>\r\n" + 
				"                </item>\r\n" + 
				"            </cotizacionEnvio>\r\n" + 
				"        </ns1:CotizarEnvio>\r\n" + 
				"    </env:Body>\r\n" + 
				"</env:Envelope>\r\n" + 
				"";

    	 StringEntity stringEntity = new StringEntity(body2, "UTF-8");
		
        stringEntity.setChunked(true);
        HttpPost httpPost = new HttpPost(urlCotizar);
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("SOAPAction", "soapAction");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
        SAXParser sAXParser=saxParseFactory.newSAXParser();

        String numero="";
        String strResponse = null;
        if (entity != null) {
            strResponse = EntityUtils.toString(entity);
            /*SucursalHandler handlerS=new SucursalHandler();
            sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerS);
            ArrayList<ResultadoConsultarSucursales> sucursaleses=handlerS.getResultadoSucursales();
            for (ResultadoConsultarSucursales versione : sucursaleses) {
            	numero=versione.getNumero();
                System.out.println("versione.getNumero:"+versione.getNumero());
            
            }*/
            
            CotizarHandler handlerC=new CotizarHandler();
            sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerC);
             ArrayList<CotizarEnvioResponse> cotizarEnvioResponses=handlerC.getCotizarEnvioResponse();
            for (CotizarEnvioResponse versione : cotizarEnvioResponses) {
            	numero=versione.getTarifa();
                System.out.println(" log versione:"+versione);
            
            }
        }
        
        System.out.println("strResponse:"+convertiraISO(strResponse));
        return ""+numero;

     }
     
     
     public static String convertiraISO(String s) {
         String out = null;
         try {
             out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
         } catch (java.io.UnsupportedEncodingException e) {
             return null;
         }
         return out;
     }
     
     
     public String andreaniEnviar(Yng_Envio envio) throws Exception{
    	// saltarSSl();
    	 System.out.println("envio:"+envio.toString());
    	 
    	 
 	 	String body2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
 	 			"<SOAP-ENV:Envelope\r\n" + 
 	 			"    xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\r\n" + 
 	 			"    xmlns:ns1=\"http://www.andreani.com.ar\"\r\n" + 
 	 			"    xmlns:ns2=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\r\n" + 
 	 			"    <SOAP-ENV:Header>\r\n" + 
 	 			"        <ns2:Security SOAP-ENV:mustUnderstand=\"1\">\r\n" + 
 	 			"            <ns2:UsernameToken>\r\n" + 
 	 			"                <ns2:Username>STAGING_WS</ns2:Username>\r\n" + 
 	 			"                <ns2:Password>ANDREANI</ns2:Password>\r\n" + 
 	 			"            </ns2:UsernameToken>\r\n" + 
 	 			"        </ns2:Security>\r\n" + 
 	 			"    </SOAP-ENV:Header>\r\n" + 
 	 			"    <SOAP-ENV:Body>\r\n" + 
 	 			"        <ns1:ConfirmarCompra>\r\n" + 
 	 			"            <ns1:compra>\r\n" + 
 	 			"                <ns1:Calle>calle salta </ns1:Calle>\r\n" + 
 	 			"                <ns1:CategoriaDistancia>1</ns1:CategoriaDistancia>\r\n" + 
 	 			"                <ns1:CategoriaFacturacion>1</ns1:CategoriaFacturacion>\r\n" + 
 	 			"                <ns1:CategoriaPeso>1</ns1:CategoriaPeso>\r\n" + 
 	 			"                <ns1:CodigoPostalDestino>4400</ns1:CodigoPostalDestino>\r\n" + 
 	 			"                <ns1:Contrato>400006711</ns1:Contrato>\r\n" + 
 	 			"                <ns1:Departamento></ns1:Departamento>\r\n" + 
 	 			"                <ns1:DetalleProductosEntrega></ns1:DetalleProductosEntrega>\r\n" + 
 	 			"                <ns1:DetalleProductosRetiro></ns1:DetalleProductosRetiro>\r\n" + 
 	 			"                <ns1:Email>patzidanil@gmail.com</ns1:Email>\r\n" + 
 	 			"                <ns1:Localidad>salta</ns1:Localidad>\r\n" + 
 	 			"                <ns1:NombreApellido>daniel</ns1:NombreApellido>\r\n" + 
 	 			"                <ns1:NombreApellidoAlternativo>juan</ns1:NombreApellidoAlternativo>\r\n" + 
 	 			"                <ns1:Numero>1234</ns1:Numero>\r\n" + 
 	 			"                <ns1:NumeroCelular>60105552</ns1:NumeroCelular>\r\n" + 
 	 			"                <ns1:NumeroDocumento>9070532</ns1:NumeroDocumento>\r\n" + 
 	 			"                <ns1:NumeroTelefono>28909090</ns1:NumeroTelefono>\r\n" + 
 	 			"                <ns1:NumeroTransaccion></ns1:NumeroTransaccion>\r\n" + 
 	 			"                <ns1:Peso>500</ns1:Peso>\r\n" + 
 	 			"                <ns1:Piso>5</ns1:Piso>\r\n" + 
 	 			"                <ns1:Provincia>salta</ns1:Provincia>\r\n" + 
 	 			"                <ns1:SucursalCliente></ns1:SucursalCliente>\r\n" + 
 	 			"                <ns1:SucursalRetiro>17</ns1:SucursalRetiro>\r\n" + 
 	 			"                <ns1:Tarifa>158</ns1:Tarifa>\r\n" + 
 	 			"                <ns1:TipoDocumento>DNI</ns1:TipoDocumento>\r\n" + 
 	 			"                <ns1:ValorACobrar>1</ns1:ValorACobrar>\r\n" + 
 	 			"                <ns1:ValorDeclarado>1600</ns1:ValorDeclarado>\r\n" + 
 	 			"                <ns1:Volumen>1800</ns1:Volumen>\r\n" + 
 	 			"            </ns1:compra>\r\n" + 
 	 			"        </ns1:ConfirmarCompra>\r\n" + 
 	 			"    </SOAP-ENV:Body>\r\n" + 
 	 			"</SOAP-ENV:Envelope>\r\n" + 
 	 			"";
 	 	TrustManager[] trustAllCerts = new TrustManager[]{
 	 	        new X509TrustManager() {

 	 	            public java.security.cert.X509Certificate[] getAcceptedIssuers()
 	 	            {
 	 	                return null;
 	 	            }
 	 	            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
 	 	            {
 	 	                //No need to implement.
 	 	            }
 	 	            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
 	 	            {
 	 	                //No need to implement.
 	 	            }
 	 	        }
 	 	};

 	 	// Install the all-trusting trust manager
 	 	try 
 	 	{
 	 	    SSLContext sc = SSLContext.getInstance("SSL");
 	 	    sc.init(null, trustAllCerts, new java.security.SecureRandom());
 	 	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
 	 	} 
 	 	catch (Exception e) 
 	 	{
 	 	    System.out.println(e);
 	 	}
 	 	

 	 StringEntity stringEntity = new StringEntity(body2, "UTF-8");
 	
     stringEntity.setChunked(true);
     HttpPost httpPost = new HttpPost(urlComprar);
    // httpPost.set
     httpPost.setEntity(stringEntity);
     httpPost.addHeader("Content-Type", "text/xml");
     httpPost.addHeader("SOAPAction", "soapAction");
     HttpClient httpClient = new DefaultHttpClient();
     HttpResponse response = httpClient.execute(httpPost);
     HttpEntity entity = response.getEntity();
     SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
     SAXParser sAXParser=saxParseFactory.newSAXParser();

     String numero="";
     String strResponse = null;
     if (entity != null) {
         strResponse = EntityUtils.toString(entity);

          EnvioHandler handlerE=new EnvioHandler();
          sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerE);
           ArrayList<EnvioResponce> envioResponses=handlerE.getEnvioResponse();
           System.out.println("EnvioResponce1:");
          for (EnvioResponce versione : envioResponses) {
        	  numero=versione.getNumeroAndreani();
              System.out.println("EnvioResponce:"+versione);
          
          }
     }
     
     System.out.println("strResponse:"+convertiraISO(strResponse));
     return ""+numero;

  }
  
     public String andreanImprimirLink(Yng_Envio envio) throws Exception{ 
  	 	String body2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
  	 			"<SOAP-ENV:Envelope\r\n" + 
  	 			"    xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\r\n" + 
  	 			"    xmlns:ns1=\"http://www.andreani.com.ar\"\r\n" + 
  	 			"    xmlns:ns2=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\r\n" + 
  	 			"    <SOAP-ENV:Header>\r\n" + 
  	 			"        <ns2:Security SOAP-ENV:mustUnderstand=\"1\">\r\n" + 
  	 			"            <ns2:UsernameToken>\r\n" + 
  	 			"                <ns2:Username>"
  	 			+ "STAGING_WS"
  	 			+ "</ns2:Username>\r\n" + 
  	 			"                <ns2:Password>"
  	 			+ "ANDREANI"
  	 			+ "</ns2:Password>\r\n" + 
  	 			"            </ns2:UsernameToken>\r\n" + 
  	 			"        </ns2:Security>\r\n" + 
  	 			"    </SOAP-ENV:Header>\r\n" + 
  	 			"    <SOAP-ENV:Body>\r\n" + 
  	 			"        <ns1:ImprimirConstancia>\r\n" + 
  	 			"            <ns1:entities>\r\n" + 
  	 			"                <ns1:ParamImprimirConstancia>\r\n" + 
  	 			"                    <ns1:NumeroAndreani>"
  	 			+ envio.getNumeroAndreani()
  	 			+ "</ns1:NumeroAndreani>\r\n" + 
  	 			"                </ns1:ParamImprimirConstancia>\r\n" + 
  	 			"            </ns1:entities>\r\n" + 
  	 			"        </ns1:ImprimirConstancia>\r\n" + 
  	 			"    </SOAP-ENV:Body>\r\n" + 
  	 			"</SOAP-ENV:Envelope>";

  	 StringEntity stringEntity = new StringEntity(body2, "UTF-8");
 		
      stringEntity.setChunked(true);
      HttpPost httpPost = new HttpPost(urlCotizar);
      httpPost.setEntity(stringEntity);
      httpPost.addHeader("Content-Type", "text/xml");
      httpPost.addHeader("SOAPAction", "soapAction");
      HttpClient httpClient = new DefaultHttpClient();
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
      SAXParser sAXParser=saxParseFactory.newSAXParser();
      

      String numero="";
      String strResponse = null;
      if (entity != null) {
          strResponse = EntityUtils.toString(entity);

           ImprimirConstanciaHandler handlerE=new ImprimirConstanciaHandler();
           sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerE);
            ArrayList<ImprimirConstanciaResponse> envioResponses=handlerE.getImprimirResponce();
            System.out.println("EnvioResponce1:");
           for (ImprimirConstanciaResponse versione : envioResponses) {
         	  numero=versione.getPdfLinkFile();
               System.out.println("EnvioResponce:"+versione);
           
           }
      }
      
      System.out.println("strResponse:"+convertiraISO(strResponse));
      return ""+numero;
      
   
      

   }
  

 	
 	 public List<ResultadoConsultarSucursales> andreaniSucursalList(Yng_AndreaniSucursal cot) throws Exception{ 
 		 System.out.println("cot::"+cot.toString());
    	String body3="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
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
				+ cot.getPassword()
				+ "</ns3:Username>\r\n" + 
				"                <ns3:Password>"
				+ cot.getPassword()
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
				+ cot.getLocalidad()
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">CodigoPostal</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ cot.getCodigoPostal()
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Provincia</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ cot.getProvincia()
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                    </value>\r\n" + 
				"                </item>\r\n" + 
				"            </Consulta>\r\n" + 
				"        </ns1:ConsultarSucursales>\r\n" + 
				"    </env:Body>\r\n" + 
				"</env:Envelope>";
		

    	 
    	 StringEntity stringEntity = new StringEntity(body3, "UTF-8");
		
        stringEntity.setChunked(true);
        HttpPost httpPost = new HttpPost(urlSuc);
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("SOAPAction", "soapAction");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
        SAXParser sAXParser=saxParseFactory.newSAXParser();

        String numero="";
        String strResponse = null;
     List<ResultadoConsultarSucursales> sucursal = null;
        if (entity != null) {
            strResponse = EntityUtils.toString(entity);
            SucursalHandler handlerS=new SucursalHandler();
            sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerS);
            ArrayList<ResultadoConsultarSucursales> sucursaleses=handlerS.getResultadoSucursales();
            sucursal=handlerS.getResultadoSucursales();
            for (ResultadoConsultarSucursales versione : sucursaleses) {
            	numero=versione.getNumero();
                System.out.println("versione.getNumero:"+versione.getNumero());
                
            
            }
        }
        
        // List<ResultadoConsultarSucursales> sucursal
        
        System.out.println("strResponse:"+convertiraISO(strResponse));
        return sucursal;

     }
 	 
  	@RequestMapping(value = "/branch", method = RequestMethod.POST)
  	@ResponseBody
      public List<ResultadoConsultarSucursales> sucursalesList(@Valid @RequestBody Yng_AndreaniSucursal suc){
 		System.out.println(suc.toString());
    	  List<ResultadoConsultarSucursales> sucursal = null;
    	  Yng_AndreaniSucursal cot=new Yng_AndreaniSucursal();
    	  cot.setUsername("");
    	  cot.setPassword("");
    	  cot.setCodigoPostal("");
    	  cot.setLocalidad("");
    	  cot.setProvincia("");
    	  cot=suc;
    	  cot.setUsername("STAGING_WS");
    	  cot.setPassword("ANDREANI");
    	  System.out.println(cot.toString());
		try {
			sucursal = andreaniSucursalList(cot);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  return sucursal;
      }
 	@RequestMapping(value = "/cotizarAndreani", method = RequestMethod.POST)
	@ResponseBody
    public CotizarEnvioResponse andreaniCotizarList(@Valid @RequestBody Yng_AndreaniCotizacion cotizar) throws MessagingException {
 		
 		CotizarEnvioResponse cot=null;
 		Yng_AndreaniCotizacion cotizarTemp=cotizar;
 		System.out.println(cotizarTemp.toString()); 		
 		List<Yng_Cotizar>cotizarList;
 		String ta="";
 		try {
 			cot=andreaniCotiza(cotizarTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		ta=cot.getTarifa();
 		System.out.println("tarifa:"+ta);
 		//Yng_Standard standard= new Yng_Standard();
 		standard=standardService.findByKey("shippingPercentage");
 		double porce = 1+Double.parseDouble(standard.getValue())/100;
 		double doble = Double.parseDouble(ta)*porce;
 		doble=redondearDecimales(doble, 2);
 		System.out.println("tarifaFinal:"+doble);
 		cot.setTarifa(""+doble);
 		return cot;
 	}
    public CotizarEnvioResponse andreaniCotiza(Yng_AndreaniCotizacion cotizarTemp) throws Exception{ 
    	TrustManager[] trustAllCerts = new TrustManager[]{
    	        new X509TrustManager() {

    	            public java.security.cert.X509Certificate[] getAcceptedIssuers()
    	            {
    	                return null;
    	            }
    	            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
    	            {
    	                //No need to implement.
    	            }
    	            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
    	            {
    	                //No need to implement.
    	            }
    	        }
    	};

    	// Install the all-trusting trust manager
    	try 
    	{
    	    SSLContext sc = SSLContext.getInstance("SSL");
    	    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    	} 
    	catch (Exception e) 
    	{
    	    System.out.println(e);
    	}
    	standard= new Yng_Standard();
    	standard=standardService.findByKey("Username");
    	 cotizarTemp.setUsername(standard.getValue());
    	 standard=standardService.findByKey("Password");
    	 cotizarTemp.setPassword(standard.getValue());
    	 
	 	String body2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"<env:Envelope\r\n" + 
			"    xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"\r\n" + 
			"    xmlns:ns1=\"urn:CotizarEnvio\"\r\n" + 
			"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
			"    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\r\n" + 
			"    xmlns:ns2=\"http://xml.apache.org/xml-soap\"\r\n" + 
			"    xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\r\n" + 
			"    xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
			"    <env:Header>\r\n" + 
			"        <ns3:Security env:mustUnderstand=\"true\">\r\n" + 
			"            <ns3:UsernameToken>\r\n" + 
			"                <ns3:Username>"
			+ cotizarTemp.getUsername()
			+ "</ns3:Username>\r\n" + 
			"                <ns3:Password>"
			+ cotizarTemp.getPassword()
			+ "</ns3:Password>\r\n" + 
			"            </ns3:UsernameToken>\r\n" + 
			"        </ns3:Security>\r\n" + 
			"    </env:Header>\r\n" + 
			"    <env:Body>\r\n" + 
			"        <ns1:CotizarEnvio env:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
			"            <cotizacionEnvio xsi:type=\"ns2:Map\">\r\n" + 
			"                <item>\r\n" + 
			"                    <key xsi:type=\"xsd:string\">cotizacionEnvio</key>\r\n" + 
			"                    <value xsi:type=\"ns2:Map\">\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">CPDestino</key>\r\n" + 
			"                            <value xsi:type=\"xsd:string\">"
			+ cotizarTemp.getCodigoPostal()
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">Cliente</key>\r\n" + 
			"                            <value xsi:type=\"xsd:string\">"
			+ "CL0003750"
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">Contrato</key>\r\n" + 
			"                            <value xsi:type=\"xsd:string\">"
			+ "400006711"
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">Peso</key>\r\n" + 
			"                            <value xsi:type=\"xsd:int\">"
			+ cotizarTemp.getPeso()
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">SucursalRetiro</key>\r\n" + 
			"                            <value xsi:type=\"xsd:string\">"
			+ cotizarTemp.getCodigoDeSucursal()
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">Volumen</key>\r\n" + 
			"                            <value xsi:type=\"xsd:int\">"
			+ cotizarTemp.getVolumen()
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">ValorDeclarado</key>\r\n" + 
			"                            <value xsi:type=\"xsd:int\">"
			+ cotizarTemp.getValorDeclarado()
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                    </value>\r\n" + 
			"                </item>\r\n" + 
			"            </cotizacionEnvio>\r\n" + 
			"        </ns1:CotizarEnvio>\r\n" + 
			"    </env:Body>\r\n" + 
			"</env:Envelope>\r\n" + 
			"";

	 StringEntity stringEntity = new StringEntity(body2, "UTF-8");
	
    stringEntity.setChunked(true);
    HttpPost httpPost = new HttpPost(urlCotizar);
    httpPost.setEntity(stringEntity);
    httpPost.addHeader("Content-Type", "text/xml");
    httpPost.addHeader("SOAPAction", "soapAction");
    HttpClient httpClient = new DefaultHttpClient();
    HttpResponse response = httpClient.execute(httpPost);
    HttpEntity entity = response.getEntity();
    SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
    SAXParser sAXParser=saxParseFactory.newSAXParser();

    String numero="";
    String strResponse = null;
    CotizarEnvioResponse cot=null;
    if (entity != null) {
        strResponse = EntityUtils.toString(entity);        
        CotizarHandler handlerC=new CotizarHandler();
        sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerC);
         ArrayList<CotizarEnvioResponse> cotizarEnvioResponses=handlerC.getCotizarEnvioResponse();
        for (CotizarEnvioResponse versione : cotizarEnvioResponses) {
        	numero=versione.getTarifa();
            System.out.println(" log versione:"+versione);
            cot=versione;            
        
        }
    }
    
    System.out.println("strResponse:"+convertiraISO(strResponse));
    return cot;

 }
    
    @Autowired
    CotizacionDao cotizacionDao;
    @RequestMapping(value = "/cotizacion", method = RequestMethod.POST)
	@ResponseBody
    public String sellServicePost(@Valid @RequestBody Yng_Cotizacion cotizacion) throws MessagingException {
    	Yng_User userTemp= userDao.findByUsername(cotizacion.getIdUser());
    	cotizacion.setIdUser(""+userTemp.getUserId());
    	cotizacionDao.save(cotizacion);
    	
    	 return "save";
    }
    
    @Autowired
   EnvioDao  envioDao;
    @RequestMapping(value = "/envio", method = RequestMethod.POST)
	@ResponseBody
    public String sellServicePost(@Valid @RequestBody Yng_Envio envio) throws MessagingException {
    	Yng_Envio tempEnvio=envio;
    	Yng_Envio yi=tempEnvio;
    	AndreaniApis andrea=new AndreaniApis();
    	andreaniapis.Yng_Envio com=new andreaniapis.Yng_Envio();
   	tempEnvio.setContrato("400006711");
    	 com.setProvincia(yi.getProvincia());
         com.setLocalidad(yi.getLocalidad());
         com.setCodigoPostalDestino(yi.getCodigoPostalDestino());
         com.setCalle(yi.getCalle());
         com.setNumero(yi.getNumero());
         com.setSucursalRetiro(yi.getSucursalRetiro());
         com.setSucursalCliente(yi.getSucursalCliente());
         com.setNombreApellido(yi.getNombreApellido());
         com.setNombreApellidoAlternativo(yi.getNombreApellidoAlternativo());
         com.setTipoDocumento(yi.getTipoDocumento());
         com.setNumeroDocumento(yi.getNumeroDocumento());
         com.setEmail(yi.getEmail());
         com.setNumeroCelular(yi.getNumeroCelular());
         com.setNumeroTelefono(yi.getNumeroTelefono());
         com.setContrato("400006711");
         com.setNumeroTransaccion(yi.getNumeroTransaccion());
         com.setTarifa(yi.getTarifa());
         com.setValorACobrar(yi.getValorACobrar());
         com.setCategoriaDistancia(yi.getCategoriaDistancia());
         com.setCategoriaFacturacion(yi.getCategoriaFacturacion());
         com.setCategoriaPeso(yi.getCategoriaPeso());
         com.setDetalleProductosEntrega(yi.getDetalleProductosEntrega());
         com.setDetalleProductosRetiro(yi.getDetalleProductosRetiro());
         com.setVolumen(yi.getVolumen());
         com.setValorDeclarado(yi.getValorDeclarado());
         com.setPeso(yi.getPeso());
         /*
    	 com.setProvincia("Buenos Aires");
         com.setLocalidad("TRUJUI");
         com.setCodigoPostalDestino("1744");
         com.setCalle("Triunvirato");
         com.setNumero("2840");
         com.setSucursalRetiro("29");
         com.setSucursalCliente("");
         com.setNombreApellido("daniel");
         com.setNombreApellidoAlternativo("daniel Alternativo");
         com.setTipoDocumento("DNI");
         com.setNumeroDocumento("9070532");
         com.setEmail("daniel@valework.com");
         com.setNumeroCelular("60105552");
         com.setNumeroTelefono("28909090");
         com.setContrato("400006711");
         com.setNumeroTransaccion("1");
         com.setTarifa("10");
         com.setValorACobrar("200");
         com.setCategoriaDistancia("1");
         com.setCategoriaFacturacion("1");
         com.setCategoriaPeso("5");
         com.setDetalleProductosEntrega("");
         com.setDetalleProductosRetiro("");
         com.setVolumen("4000");
         com.setValorDeclarado("5000");
         com.setPeso("600");
*/
         
     	String codAndreani="";
     	codAndreani=andrea.confirmarEnvio(com);
     	tempEnvio.setNumeroAndreani(codAndreani);
    	String pdfLink="";
    	pdfLink=andrea.linkPdf(codAndreani);
    	System.out.println("aqui:"+1);
    	tempEnvio.setPdfLink(pdfLink);
    	
    
    	try {
    		tempEnvio.setContrato("400006711");
    		codAndreani=andreaniEnviar(tempEnvio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	tempEnvio.setNumeroAndreani(codAndreani);
    	
    	
    	try {
			pdfLink=andreanImprimirLink(tempEnvio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	tempEnvio.setPdfLink(pdfLink);
    	
    	
    	
    	
    	
    	
    	
    	envioDao.save(tempEnvio);
    	
    	 return "save";
    }
    
    public void saltarSSl(){
    	TrustManager[] trustAllCerts = new TrustManager[]{
    	        new X509TrustManager() {

    	            public java.security.cert.X509Certificate[] getAcceptedIssuers()
    	            {
    	                return null;
    	            }
    	            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
    	            {
    	                //No need to implement.
    	            }
    	            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
    	            {
    	                //No need to implement.
    	            }
    	        }
    	};

    	// Install the all-trusting trust manager
    	try 
    	{
    	    SSLContext sc = SSLContext.getInstance("SSL");
    	    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    	} 
    	catch (Exception e) 
    	{
    	    System.out.println(e);
    	}
    }
   

     
    @RequestMapping(value = "/cotizacionB", method = RequestMethod.POST)
	@ResponseBody
    public Yng_Cotizacion logisticCotiBuscar(@Valid @RequestBody Yng_Cotizacion cotizacion) throws MessagingException {
    	Yng_Cotizacion cotizacionTemp=cotizacion;
    	
    	Yng_User userTemp= userDao.findByUsername(cotizacion.getIdUser());
    	cotizacion.setIdUser(""+userTemp.getUserId());
    	cotizacionDao.save(cotizacion);
    	
    	 return cotizacionTemp;
    }
    public String printTicketData(String numberAndreni) {
 	   String imprimirEtiqueta="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:and=\"http://www.andreani.com.ar\">\r\n" + 
 		   		"   <soapenv:Header/>\r\n" + 
 		   		"   <soapenv:Body>\r\n" + 
 		   		"      <and:ImprimirConstancia>\r\n" + 
 		   		"         <!--Optional:-->\r\n" + 
 		   		"         <and:entities>\r\n" + 
 		   		"            <!--Zero or more repetitions:-->\r\n" + 
 		   		"            <and:ParamImprimirConstancia>\r\n" + 
 		   		"               <and:NumeroAndreani>"
 		   		+ numberAndreni
 		   		+ "</and:NumeroAndreani>\r\n" + 
 		   		"            </and:ParamImprimirConstancia>\r\n" + 
 		   		"         </and:entities>\r\n" + 
 		   		"      </and:ImprimirConstancia>\r\n" + 
 		   		"   </soapenv:Body>\r\n" + 
 		   		"</soapenv:Envelope>";
 		 
 	   
 	   return ""+imprimirEtiqueta;
    }
    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
    @RequestMapping(value = "/branchList", method = RequestMethod.POST)
  	@ResponseBody
      public List<Yng_Branch> branchList(@Valid @RequestBody  Yng_AndreaniSucursal suc){
 		System.out.println("sucursal:"+suc.toString());
 		
    	  List<Yng_Branch> branchShipping = new ArrayList<Yng_Branch>();
    	  List<ResultadoConsultarSucursales> sucursal = new ArrayList<ResultadoConsultarSucursales>();
    	  Yng_AndreaniSucursal cot=new Yng_AndreaniSucursal();
    	  cot.setUsername("");
    	  cot.setPassword("");
    	  cot.setCodigoPostal("");
    	  cot.setLocalidad("");
    	  cot.setProvincia("");
    	  cot=suc;
    	  cot.setUsername("STAGING_WS");
    	  cot.setPassword("ANDREANI");
   
    	  System.out.println(cot.toString());
    	  
		try {
			sucursal=andreaniSucursalList(cot);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ResultadoConsultarSucursales resultadoConsultarSucursales : sucursal) {
			Yng_Branch bra = new Yng_Branch();
			bra.setDateDelivery("");
			bra.setLocation(""+resultadoConsultarSucursales.getDescripcion());
			bra.setNameMail("Andreni");
			try {
				bra.setRespuesta(""+jsonToShipmentsUnit(resultadoConsultarSucursales));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bra.setSchedules(""+resultadoConsultarSucursales.getHoradeTrabajo());
			bra.setStreet(""+resultadoConsultarSucursales.getDireccion());
			//bra.setYng_Envio(null);		
			branchShipping.add(bra);
			//System.out.println("branchShipping:"+""+branchShipping.toString());
		}
		//System.out.println("branchShipping:"+branchShipping.toString());
       return branchShipping;
      }
      
      private String jsonToShipments(List<ResultadoConsultarSucursales> shipments) throws JsonProcessingException{
  		ObjectMapper mapper = new ObjectMapper();
  		  
  		String json = mapper.writerWithDefaultPrettyPrinter()
  		                    .writeValueAsString(shipments);

  		System.out.println("SALIDA JSON : \n" + json);
  		return ""+json;
  	}
  	private void jsonResultadoConsultarSucursaless(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
       mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
       ResultadoConsultarSucursales[] resultadoConsultarSucursales;
		try {
			resultadoConsultarSucursales= mapper.readValue(json, ResultadoConsultarSucursales[].class);
			System.out.println("tamaño suc:"+resultadoConsultarSucursales.length);
			 System.out.println("resultadoConsultarSucursales: "+resultadoConsultarSucursales[0].toString());		 
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
    private String jsonToShipmentsUnit(ResultadoConsultarSucursales shipments) throws JsonProcessingException{
 		ObjectMapper mapper = new ObjectMapper(); 		  
 		String json = mapper.writerWithDefaultPrettyPrinter()
 		                    .writeValueAsString(shipments);
 		return ""+json;
 	}
    private String jsonToQuoteUnit(CotizarEnvioResponse shipments) throws JsonProcessingException{
 		ObjectMapper mapper = new ObjectMapper(); 		  
 		String json = mapper.writerWithDefaultPrettyPrinter()
 		                    .writeValueAsString(shipments);
 		return ""+json;
 	}
    @RequestMapping(value = "/quote", method = RequestMethod.POST)
  	@ResponseBody
      public List<Yng_Quote> quote(@Valid @RequestBody  Yng_Quote quo){
    	  List<Yng_Quote> quotesList=new ArrayList<Yng_Quote>();
    	  Yng_Quote quote=new Yng_Quote();
    	  Yng_Quote quoteFedex=new Yng_Quote();
    	  quote=quo;
    	  String postalCode=quote.getYng_User().getYng_Ubication().getPostalCode();
    	  quote.getYng_Item().setUser(userDao.findByUsername(quote.getYng_Item().getUser().getUsername()));
    	  quote.setYng_User(userDao.findByUsername(quote.getYng_User().getUsername()));  
   		
    	  String type=quoteType(quote.getYng_Item().getItemId());
		  if(type.equals("Producto")) {	    	  
			    	  
			    	  standard= new Yng_Standard();
			    	  standard=standardService.findByKey("Username");
			     	  String UserNameAndrani=standard.getValue();
			     	  standard=standardService.findByKey("Password");
			     	  String PasswordAndreni=standard.getValue();
			     	 standard=standardService.findByKey("Contrato");
			    	  String ContratoAndreni=standard.getValue();
			    	  standard=standardService.findByKey("Cliente");
			    	  String ClienteAndreni=standard.getValue();
			
			    	  //ANDREANI sucursales  
			    	  List<Yng_Branch> branchShipping = new ArrayList<Yng_Branch>();
			    	  List<ResultadoConsultarSucursales> sucursal = new ArrayList<ResultadoConsultarSucursales>();
			    	  Yng_AndreaniSucursal cot=new Yng_AndreaniSucursal();
			    	  cot.setCodigoPostal(""+postalCode);
			    	  cot.setLocalidad("");
			    	  cot.setProvincia("");
			    	  //cot=suc;
			    	  cot.setUsername(UserNameAndrani);
			    	  cot.setPassword(PasswordAndreni);   
			    	  System.out.println("cot212:"+cot.toString());    	  
					try {
						sucursal=andreaniSucursalList(cot);
						System.out.println("sucursal:"+sucursal.toString());
			
					} catch (Exception e) {
						e.printStackTrace();
					}
					for (ResultadoConsultarSucursales resultadoConsultarSucursales : sucursal) {
						Yng_Branch bra = new Yng_Branch();
						bra.setDateDelivery("");
						bra.setLocation(""+resultadoConsultarSucursales.getDescripcion());
						bra.setNameMail("Andreani");
						try {
							bra.setRespuesta(""+jsonToShipmentsUnit(resultadoConsultarSucursales));
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						bra.setSchedules(""+resultadoConsultarSucursales.getHoradeTrabajo());
						bra.setStreet(""+resultadoConsultarSucursales.getDireccion());
						//bra.setYng_Envio(null);		
						branchShipping.add(bra);
						quote.setYng_Branch(bra);
						//System.out.println("branchShipping:"+""+branchShipping.toString());
					}
					if(!branchShipping.isEmpty()) {
						quote.setYng_Branch(branchShipping.get(0));
					}    	   	  
			    	  //
			    	  Yng_AndreaniCotizacion cotizarAndreani=new Yng_AndreaniCotizacion();
			    	  
			    	  cotizarAndreani.setCodigoDeCliente(ClienteAndreni);
			    	  cotizarAndreani.setCodigoDeSucursal(""+quote.getYng_Item().getYng_Ubication().getCodAndreani());
			    	  System.out.println("postal:"+postalCode);
			    	  cotizarAndreani.setCodigoPostal(postalCode);//quote.getYng_User().getYng_Ubication().getPostalCode()
			    	  cotizarAndreani.setNumeroDeContrato(ContratoAndreni);
			    	  cotizarAndreani.setPassword(PasswordAndreni);
			    	  cotizarAndreani.setUsername(UserNameAndrani);
			    	  Yng_Product getProductByIdItem=new Yng_Product();
			    	  getProductByIdItem=getProductByIdItem(quote.getYng_Item().getItemId());
			    	  
			    	  int peso=0;
			    	  peso=getProductByIdItem.getProductWeight();
			    	  peso=Integer.parseInt(getProductByIdItem.getProductPeso());
			    	  cotizarAndreani.setPeso(""+(peso*quo.getQuantity()));
			    	  cotizarAndreani.setValorDeclarado(""+quote.getYng_Item().getPrice());
			    	  
			    	  int volumen=0;
			    	  volumen=getProductByIdItem.getProductHeight()*getProductByIdItem.getProductLength()*getProductByIdItem.getProductWidth();
			    	  volumen=Integer.parseInt(getProductByIdItem.getProducVolumen());
			    	  cotizarAndreani.setVolumen(""+(volumen*quo.getQuantity()));
			    	  CotizarEnvioResponse andreaniQuote=new CotizarEnvioResponse();
				    	  try {
				    		  andreaniQuote=andreaniQuote(cotizarAndreani);
				    		  System.out.println("pro:"+andreaniQuote.toString());
				    		  
						} catch (MessagingException e) {

							e.printStackTrace();
						}			    	  
			    	  double rate = Double.parseDouble(andreaniQuote.getTarifa());
			    	  String ta="";		    	   		
		    	   		standard=standardService.findByKey("shippingPercentage");
		    	   		double porce = 1+Double.parseDouble(standard.getValue())/100;
		    	   		double doble = rate*porce;
		    	   		doble=redondearDecimales(doble, 2);
			    	  quote.setRate(doble);
			    	  quote.setRateOrigin(rate);			    	  
			    	  try {
			    		  quote.setRespuesta(""+jsonToQuoteUnit(andreaniQuote));
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
			    	  //Fedex inicio
			    	 /* FedexXML xmlFedex=new FedexXML();
			    	  //iniciando variable de desarrolo
					    	  standard= new Yng_Standard();
					      	standard=standardService.findByKey("FedEXAuthenticationKey");
					      	FedEXAuthenticationKey= standard.getValue();
					      	
					      	standard=standardService.findByKey("FedExMeterNumber");
					      	FedExMeterNumber= standard.getValue();
					      	
					      	standard=standardService.findByKey("FedExAccountNumber");
					      	FedExAccountNumber= standard.getValue();
					      	
					      	standard=standardService.findByKey("FedexPassword");
					      	FedexPassword= standard.getValue();
					      	//inirCr
					      	xmlFedex.inirCre(FedEXAuthenticationKey, FedExMeterNumber, FedExAccountNumber, FedexPassword);
			    	  //finalizando variable de desarrolo
			    	 
			    		PropertyObjectHttp propertyObjectHttp = new PropertyObjectHttp();
			    		
						String cotizacion=xmlFedex.FedexLocation(quote);
						//obtener xml para el envio
			    		propertyObjectHttp.setBody(cotizacion);
			    		// setear el tipo de request GET, POST, PUT etc...
			    		propertyObjectHttp.setRequestMethod(propertyObjectHttp.POST);
			    		// setear el url ala que se enviara 
			    		propertyObjectHttp.setUrl("https://wsbeta.fedex.com:443/web-services");
			    			http  httoUrlcon=new http();
			    			String outputString;
			    			Yng_Branch braFedex = new Yng_Branch();
			    			FedexResponce fedex=new FedexResponce();
			    			
			    			try {
							 outputString=httoUrlcon.request(propertyObjectHttp);
							 braFedex=fedex.fedexBranch(outputString);							 
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    			//branchShipping.add(braFedex);
			    			quoteFedex.setYng_Branch(braFedex);
			    		//inicio de rate	
			    		String rateFedex=xmlFedex.FedexRate(quote, getProductByIdItem);
		    			//obtener xml para el envio
			    		propertyObjectHttp.setBody(rateFedex);
			    		// setear el tipo de request GET, POST, PUT etc...
			    		//propertyObjectHttp.setRequestMethod(propertyObjectHttp.POST);
			    		// setear el url ala que se enviara 
			    		//propertyObjectHttp.setUrl("https://wsbeta.fedex.com:443/web-services");
			    		//Yng_Quote quoteFedex=new Yng_Quote();
			    		try {
							 outputString=httoUrlcon.request(propertyObjectHttp);
							 try {
								quoteFedex=fedex.fedexQuote(outputString);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}							 
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    		double dobleF = quoteFedex.getRateOrigin()*porce;
			    		quoteFedex.setRate(dobleF);*/
			    	  //fedex fin
			    	 // quotesList.add(quoteFedex);//se comento solo para pruebas
			    	  quotesList.add(quote);			    	  
		  }
    	 return quotesList; 
      }
      
      public String quoteType(Long itemId) {
      	System.out.println("itemId:"+itemId);
  		Yng_Item yng_Item = itemDao.findByItemId(itemId);
  		List<Yng_Service> serviceList= serviceService.findByItem(yng_Item);
  		List<Yng_Product> productList= productService.findByItem(yng_Item);
  		List<Yng_Motorized> motorizedList= motorizedService.findByItem(yng_Item);
  		List<Yng_Property> propertyList= propertyService.findByItem(yng_Item);
  		//hacer pára productos, motorizados inmuebles  
  		if(serviceList.size()==1) {
  			return "Servicio";
  		}
  		if(productList.size()==1) {
  			return "Producto";
  		}
  		if(motorizedList.size()==1) {
  			return "Vehiculo";
  		}
  		if(propertyList.size()==1) {
  			return "Inmueble";
  		}
  		else {
  			return "otro";
  		}
  		
      }
      
      public CotizarEnvioResponse andreaniQuote(Yng_AndreaniCotizacion cotizar) throws MessagingException {
   		System.out.println("cotizar:"+cotizar.getCodigoPostal());
   		CotizarEnvioResponse cot=new CotizarEnvioResponse();
   		Yng_AndreaniCotizacion cotizarTemp=cotizar;
   		//System.out.println(cotizarTemp.toString()); 		
   		//List<Yng_Cotizar>cotizarList;
   		
   		try {
   			cot=andreaniCotiza(cotizar);
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
   		
   		return cot;
   	}
      
      public Yng_Product getProductByIdItem(Long itemId) {System.out.println("itemId:"+itemId);
      Yng_Item yng_Item = itemDao.findByItemId(itemId);
		List<Yng_Product> productList= productService.findByItem(yng_Item);
		Yng_Product product = productList.get(0);
		//System.out.println("pro: "+product);
		return product;	
      }
      
  	
}
