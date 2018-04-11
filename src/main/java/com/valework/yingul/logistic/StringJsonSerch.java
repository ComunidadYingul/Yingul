package com.valework.yingul.logistic;



import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class StringJsonSerch {
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
	        //System.out.println("value:"+value);
	        if (value instanceof JSONObject) {
	            finalValue = recurseKeys((JSONObject)value, findKey);
	        }
	        if(value instanceof JSONArray) {
	        	int a=((JSONArray) value).length();
	        	JSONArray s=(JSONArray) value;
	        	if(a>0) {
	        	for (int i = 0; i < a; i++) {
	        		Object valueA=s.get(i);
	        		String r=recurseKeys2((JSONObject)valueA, findKey);
	        		System.out.println("value:"+valueA);
	        		if(r.length()>0)return r;
				}
	        	}
	        	//System.out.println("length:"+((JSONArray) value).length());
	        	//System.out.println("value:"+value);
	        }
	    }

	    // key is not found
	    return finalValue;
	}
	public static String recurseKeys2(JSONObject jObj, String findKey) throws JSONException {
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
	        //System.out.println("value:"+value);
	        if (value instanceof JSONObject) {
	            finalValue = recurseKeys((JSONObject)value, findKey);
	        }
	    }

	    // key is not found
	    return finalValue;
	}
	public static String stringJsonSearch(String stringJson,String value) throws JSONException {
		JSONObject jsonObj = new JSONObject(stringJson);
		return ""+recurseKeys(jsonObj, value);
	}
	public void eliminar() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree("");//.readTree(new File("C:\\d\\json\\j.txt"));
        for (JsonNode node : jsonNode) {
            //((ObjectNode)node).remove("familyName");
            ((ObjectNode)node).remove("ServiceTypeDescription");
        }
        objectMapper.writeValue(new File("C:\\d\\json\\j2.txt"), jsonNode);
	}
	public static String arrayJson(String arrayString,String findKey) throws JSONException {
		JSONArray jsonArray = new JSONArray(arrayString);
		String ret="";
		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject json = jsonArray.getJSONObject(i);
		    ret=recurseKeys(json, findKey);
		    
		    Iterator<String> keys = json.keys();
	
		    while (keys.hasNext()) {
		        String key = keys.next();
		       // System.out.println("Key :" + key + "  Value :" + json.get(key));
		    }
	
		}
		return ""+ret;
	}
}
