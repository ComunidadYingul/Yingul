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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valework.yingul.model.Yng_StateShipping;


public class GetTraceability {
	
	public Yng_AndreaniTrazabilidad sendState(String number,String Cliente) throws Exception {
		Yng_AndreaniTrazabilidad shStateShipping=new Yng_AndreaniTrazabilidad();
		AndreaniProperty an =new AndreaniProperty();
		shStateShipping=this.parserString(seg(an,number,Cliente));
		return shStateShipping;
	   }
	 public String seg( AndreaniProperty an,String numberAndreani,String Cliente) throws Exception{
		   AndreaniProperty andr=new AndreaniProperty();
		   andr.setHost("www.e-andreani.com");
		   andr.setWsURL("https://www.e-andreani.com/eAndreaniWS/Service.svc/soap12");
		   andr.setSOAPAction("");
		   andr.setXmlInput(andreaniXmlState(numberAndreani,Cliente));		   
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
		   		"    xmlns:ns2=\"http://www.andreani.com.ar\">\r\n" + 
		   		"    <env:Body>\r\n" + 
		   		"        <ns2:ObtenerTrazabilidadCodificado>\r\n" + 
		   		"            <ns2:NroPieza>\r\n" + 
		   		"                <ns1:NroPieza></ns1:NroPieza>\r\n" + 
		   		"                <ns1:NroAndreani>"
		   		+ ""+NumeroAndreani
		   		+ "</ns1:NroAndreani>\r\n" + 
		   		"                <ns1:CodigoCliente>"
		   		+ ""+CodigoCliente
		   		+ "</ns1:CodigoCliente>\r\n" + 
		   		"            </ns2:NroPieza>\r\n" + 
		   		"        </ns2:ObtenerTrazabilidadCodificado>\r\n" + 
		   		"    </env:Body>\r\n" + 
		   		"</env:Envelope>\r\n" + 
		   		"";
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
			httpConn.setRequestProperty("Content-Type", "application/soap+xml;charset=UTF-8;action=\"http://www.andreani.com.ar/IService/ObtenerTrazabilidadCodificado\"");
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
		      
	   public Yng_AndreaniTrazabilidad parserString(String objet) throws JSONException{
		   System.out.println("objet:"+objet);
		   Yng_AndreaniTrazabilidad andreaniTrazabilidad=new Yng_AndreaniTrazabilidad();
		   String moneda="";
		   try {
				JSONObject xmlJSONObj = XML.toJSONObject(objet);
				System.out.println("xmlJSONObj:"+xmlJSONObj);
				String sin=xmlJSONObj.toString();
				sin=sin.replaceAll("a:", "");
				  moneda=StringJsonSerch.stringJsonSearch(sin, "Envio_");			  
				System.out.println("Envio_:"+moneda);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			ObjectMapper mapper = new ObjectMapper();
			//String s="{\"FechaAlta\":\"2018-08-16T13:22:52-03:00\",\"Eventos\":{\"Evento_\":[{\"Sucursal\":\"-\",\"Fecha\":\"2018-08-16T13:22:52-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"30\",\"Estado\":\"Envío no ingresado\"},{\"Sucursal\":\"Sucursal Moreno (Bs. As.)\",\"Fecha\":\"2018-08-16T14:53:57-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"25\",\"Estado\":\"Envío ingresado al circuito operativo\"},{\"Sucursal\":\"Sucursal Moreno (Bs. As.)\",\"Fecha\":\"2018-08-16T18:29:29-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"21\",\"Estado\":\"Envío en tránsito a sucursal de Andreani\"},{\"Sucursal\":\"Planta de Operaciones Bs. As.\",\"Fecha\":\"2018-08-16T20:16:47-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"20\",\"Estado\":\"Envío en sucursal de Andreani\"},{\"Sucursal\":\"Planta de Operaciones Bs. As.\",\"Fecha\":\"2018-08-17T04:16:17-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"21\",\"Estado\":\"Envío en tránsito a sucursal de Andreani\"},{\"Sucursal\":\"Sucursal San Miguel (Bs. As.)\",\"Fecha\":\"2018-08-17T07:16:37-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"17\",\"Estado\":\"Envío en custodia en sucursal de Andreani\"}]},\"NombreEnvio\":\"Encomienda\",\"NroAndreani\":\"310000005335214\"}";
			Yng_AndreaniTrazabilidad tempTra = new Yng_AndreaniTrazabilidad();
			JSONObject jsonObj;
			if (moneda.length()>0) {
				try {
					jsonObj = new JSONObject(moneda);
					tempTra.setFechaAlta(jsonObj.optString("FechaAlta"));
					tempTra.setNombreEnvio(jsonObj.optString("NombreEnvio"));
					tempTra.setNroAndreani(jsonObj.optString("NroAndreani"));
					Yng_AndreaniTrazabilidadEventos eventos=new Yng_AndreaniTrazabilidadEventos();			
					List<Yng_AndreaniTrazabilidadEvento> evento_=event(StringJsonSerch.stringJsonSearch(moneda, "Evento_"));
		
					eventos.setEvento_(evento_);
					tempTra.setEventos(eventos);
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else {
				return null;
			}

			return tempTra;
		//return andreaniTrazabilidad;
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
	   public String andreaniXmlState(String numA,String cliente) {
		   String s="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
		   		"<env:Envelope\r\n" + 
		   		"    xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"\r\n" + 
		   		"    xmlns:ns1=\"http://www.andreani.com.ar/req\"\r\n" + 
		   		"    xmlns:ns2=\"http://www.andreani.com.ar\">\r\n" + 
		   		"    <env:Body>\r\n" + 
		   		"        <ns2:ObtenerTrazabilidadCodificado>\r\n" + 
		   		"            <ns2:NroPieza>\r\n" + 
		   		"                <ns1:NroPieza></ns1:NroPieza>\r\n" + 
		   		"                <ns1:NroAndreani>"
		   		+ numA
		   		+ "</ns1:NroAndreani>\r\n" + 
		   		"                <ns1:CodigoCliente>"
		   		+ cliente
		   		+ "</ns1:CodigoCliente>\r\n" + 
		   		"            </ns2:NroPieza>\r\n" + 
		   		"        </ns2:ObtenerTrazabilidadCodificado>\r\n" + 
		   		"    </env:Body>\r\n" + 
		   		"</env:Envelope>\r\n" + 
		   		"";
		   	   return ""+s;
		      }
		public List<Yng_AndreaniTrazabilidadEvento> event(String s3){
			System.out.println("s3:"+s3);
			//String ss="[{\"Sucursal\":\"-\",\"Fechsa\":\"2018-08-16T13:22:52-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"30\",\"Estado\":\"Envío no ingresado\"},{\"Sucursal\":\"Sucursal Moreno (Bs. As.)\",\"Fecha\":\"2018-08-16T14:53:57-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"25\",\"Estado\":\"Envío ingresado al circuito operativo\"},{\"Sucursal\":\"Sucursal Moreno (Bs. As.)\",\"Fecha\":\"2018-08-16T18:29:29-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"21\",\"Estado\":\"Envío en tránsito a sucursal de Andreani\"},{\"Sucursal\":\"Planta de Operaciones Bs. As.\",\"Fecha\":\"2018-08-16T20:16:47-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"20\",\"Estado\":\"Envío en sucursal de Andreani\"},{\"Sucursal\":\"Planta de Operaciones Bs. As.\",\"Fecha\":\"2018-08-17T04:16:17-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"21\",\"Estado\":\"Envío en tránsito a sucursal de Andreani\"},{\"Sucursal\":\"Sucursal San Miguel (Bs. As.)\",\"Fecha\":\"2018-08-17T07:16:37-03:00\",\"IdMotivo\":\"-1\",\"Motivo\":{},\"IdEstado\":\"17\",\"Estado\":\"Envío en custodia en sucursal de Andreani\"}]";
			List<Yng_AndreaniTrazabilidadEvento> re=new ArrayList<>();
			JSONArray jsonArray;
			if(s3.contains("[")) {
				try {
					jsonArray = new JSONArray(s3);
					for (int i = 0; i < jsonArray.length(); i++) {
						Yng_AndreaniTrazabilidadEvento state=new Yng_AndreaniTrazabilidadEvento();
					    JSONObject object = jsonArray.getJSONObject(i);
					    //String Fecha = object.optString("Fecha");
					    state.setEstado(object.optString("Estado"));
					    state.setFecha(object.optString("Fecha"));
					    state.setIdEstado(object.optString("IdEstado"));
					    state.setIdMotivo(object.optString("IdMotivo"));
					    state.setMotivo(null);
					    state.setSucursal(object.optString("Sucursal"));
					    re.add(state);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				jsonArray = new JSONArray();
				try {
					Yng_AndreaniTrazabilidadEvento state=new Yng_AndreaniTrazabilidadEvento();
				    JSONObject object = new JSONObject(s3);
				    //String Fecha = object.optString("Fecha");
				    state.setEstado(object.optString("Estado"));
				    state.setFecha(object.optString("Fecha"));
				    state.setIdEstado(object.optString("IdEstado"));
				    state.setIdMotivo(object.optString("IdMotivo"));
				    state.setMotivo(null);
				    state.setSucursal(object.optString("Sucursal"));
				    re.add(state);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			return re;
		}
		public boolean serchState(Yng_AndreaniTrazabilidad andreaniTrazabilidad,String buscar) {
			List<Yng_AndreaniTrazabilidadEvento> eventos=andreaniTrazabilidad.getEventos().getEvento_();
			for (Yng_AndreaniTrazabilidadEvento yng_AndreaniTrazabilidadEvento : eventos) {
				if(yng_AndreaniTrazabilidadEvento.getEstado().equals(buscar)) {
					return true;
				}					
			}
			
			return false;
		}
}
