package com.valework.yingul.logistic;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.valework.yingul.model.Yng_StateShipping;


public class GetStateSend {
	
	public Yng_StateShipping sendState(String number,String Cliente) throws Exception {
		Yng_StateShipping shStateShipping=new Yng_StateShipping();
		AndreaniProperty an =new AndreaniProperty();
		shStateShipping=this.parserString(seg(an,number,Cliente));
		return shStateShipping;
	   }
	 public String seg( AndreaniProperty an,String numberAndreani,String Cliente) throws Exception{
		   AndreaniProperty andr=new AndreaniProperty();
		   andr.setHost("www.e-andreani.com");
		   andr.setWsURL("https://www.e-andreani.com/eAndreaniWS/Service.svc/soap12");
		   andr.setSOAPAction("");
		   //headers he=new  headers();
		   //he.setName("");
		   //he.setValue("");
		  // Li
		   andr.setXmlInput(andreaniXmlSeguimiento(numberAndreani,Cliente));
		   //aniem();
		   
		   return someMethod2(andr);
	   }
	   public String andreaniXmlSeguimiento(String numA,String cliente) {
		   String s="<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" "
		   		+ "xmlns:and=\"http://www.andreani.com.ar\" "
		   		+ "xmlns:req=\"http://www.andreani.com.ar/req\">\r\n" + 
		   		"   <soap:Header/>\r\n" + 
		   		"   <soap:Body>\r\n" + 
		   		"      <and:ObtenerTrazabilidad>\r\n" + 
		   		"         <!--Optional:-->\r\n" + 
		   		"         <and:Pieza>\r\n" + 
		   		"            <req:NroPieza></req:NroPieza>\r\n" + 
		   		"            <req:NroAndreani>"
		   		+ numA
		   		+ "</req:NroAndreani>\r\n" + 
		   		"            <req:CodigoCliente>"
		   		+ ""+cliente
		   		+ "</req:CodigoCliente>\r\n" + 
		   		"         </and:Pieza>\r\n" + 
		   		"      </and:ObtenerTrazabilidad>\r\n" + 
		   		"   </soap:Body>\r\n" + 
		   		"</soap:Envelope>";
		   	   return ""+s;
		      }
	   public String andreaniStateShip(String CodigoCliente,String NumeroAndreani) {
		   return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
		   		"<env:Envelope\r\n" + 
		   		"    xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"\r\n" + 
		   		"    xmlns:ns1=\"http://www.andreani.com.ar/req\"\r\n" + 
		   		"    xmlns:ns2=\"http://schemas.datacontract.org/2004/07/EAndreaniWS\"\r\n" + 
		   		"    xmlns:ns3=\"http://www.andreani.com.ar\">\r\n" + 
		   		"    <env:Body>\r\n" + 
		   		"        <ns3:ObtenerEstadoDistribucionCodificado>\r\n" + 
		   		"            <ns3:EnviosConsultas>\r\n" + 
		   		"                <ns1:CodigoCliente>"
		   		+ ""+CodigoCliente
		   		+ "</ns1:CodigoCliente>\r\n" + 
		   		"                <ns1:Envios>\r\n" + 
		   		"                    <ns2:Envio>\r\n" + 
		   		"                        <ns2:IdentificadorCliente></ns2:IdentificadorCliente>\r\n" + 
		   		"                        <ns2:NumeroAndreani>"
		   		+ ""+NumeroAndreani
		   		+ "</ns2:NumeroAndreani>\r\n" + 
		   		"                    </ns2:Envio>\r\n" + 
		   		"                </ns1:Envios>\r\n" + 
		   		"            </ns3:EnviosConsultas>\r\n" + 
		   		"        </ns3:ObtenerEstadoDistribucionCodificado>\r\n" + 
		   		"    </env:Body>\r\n" + 
		   		"</env:Envelope>";
	   }
	   public String someMethod2(AndreaniProperty andreaniProp) throws MalformedURLException, IOException {
			//Code to make a webservice HTTP request
			System.out.println("andreaniProp:"+andreaniProp.toString());
		   	String responseString = "";
			String outputString = "";
			URL url = new URL(andreaniProp.getWsURL());
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			String xmlInput = andreaniProp.getXmlInput();
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			//List<>
			httpConn.setRequestProperty("Content-Length",String.valueOf(b.length));
			httpConn.setRequestProperty("Accept-Encoding", "gzip,deflate");
			httpConn.setRequestProperty("Content-Type", "application/soap+xml;charset=UTF-8;action=\"http://www.andreani.com.ar/IService/ObtenerTrazabilidad\"");
			httpConn.setRequestProperty("SOAPAction", andreaniProp.getSOAPAction());
			httpConn.setRequestProperty("Host",andreaniProp.getHost());
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Apache-HttpClient/4.1.1");
			httpConn.setRequestMethod("POST");
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
			//System.out.println("outputString:"+in.re);
			//Write the SOAP message response to a String.
			int a=0;
			String head="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			int sizeHead=head.length();
			while ((responseString = in.readLine()) != null) {
				if(responseString.length()==sizeHead) {
					
				}
				else {
					outputString = outputString + responseString;
				}
				if(a>0) {
					outputString = outputString + responseString;
				}	a++;	
			System.out.println("outputString:"+responseString);
			}
			System.out.println("outputString1 :"+outputString);
			try {
				JSONObject xmlJSONObj = XML.toJSONObject(outputString);
				System.out.println("xmlJSONObj:"+xmlJSONObj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ""+outputString;
			}
		      
	   public Yng_StateShipping parserString(String objet) throws JSONException{
		   //Log l=new Log();
		   JSONObject xmlJSONObj = XML.toJSONObject(objet);
		   String s=""+xmlJSONObj;
		   System.out.println("s:"+s);
		   JSONObject jObj = new JSONObject(s);
		   Yng_StateShipping shStateShipping=new Yng_StateShipping();
		   
		   String extract4 = recurseKeys(jObj, "a:FechaAlta");
		  shStateShipping.setFechaAlta(extract4);
		   
		   String estado=""+recurseKeys(jObj, "a:Estado");
		shStateShipping.setEstado(estado);
		
		   String fecha = ""+recurseKeys(jObj, "a:Fecha");
		shStateShipping.setFecha(fecha);
		   
		   String motivo=""+recurseKeys(jObj, "a:Motivo");
		shStateShipping.setMotivo(motivo);
		
		   String nombreEnvio=""+recurseKeys(jObj, "a:NombreEnvio");
		shStateShipping.setNombreEnvio(nombreEnvio);
		
		   String nroAndreani=""+recurseKeys(jObj, "a:NroAndreani");
		shStateShipping.setNroAndreani(nroAndreani);
		
		   String sucursal=""+recurseKeys(jObj, "a:Sucursal");
		shStateShipping.setSucursal(sucursal);
		   
		System.out.println("shStateShipping:"+shStateShipping.toString());
		/*System.out.println("FechaAlta:"+extract4);
		   String extract = recurseKeys(jObj, "a:Fecha");
		   System.out.println("Fecha:"+extract);
		   String extract2 = recurseKeys(jObj, "a:Estado");
		   System.out.println("a:Estado:"+extract2);
		   String extract3 = recurseKeys(jObj, "a:Sucursal");
		   System.out.println("a:Sucursal:"+extract3);
		  */
		return shStateShipping;
	   }
	   
	   public static String recurseKeys(JSONObject jObj, String findKey) throws JSONException {
		    String finalValue = "";
		    if (jObj == null) {
		        return "";
		    }

		    Iterator<String> keyItr = jObj.keys();
		    Map<String, String> map = new HashMap<>();

		    while(keyItr.hasNext()) {
		        String key = keyItr.next();
		        map.put(key, jObj.get(key).toString());
		    }

		    for (Map.Entry<String, String> e : (map).entrySet()) {
		        String key = e.getKey();
		        if (key.equalsIgnoreCase(findKey)) {
		            return jObj.get(key).toString();
		        }

		        // read value
		        Object value = jObj.get(key);

		        if (value instanceof JSONObject) {
		            finalValue = recurseKeys((JSONObject)value, findKey);
		        }
		    }

		    // key is not found
		    return finalValue;
	}

}
