package com.valework.yingul.logistic;

import java.util.ArrayList;
import java.util.List;

public class PropertyObjectHttp {
	public final  String GET="GET";
	public final  String POST="POST";
	public final  String HEAD="HEAD";
	public final  String OPTIONS="OPTIONS";
	public final  String PUT="PUT";
	public final  String DELETE="DELETE";
	public final  String TRACE ="TRACE";
	String url="";
	String body="";
	String requestMethod="";
	List<RequestPropertyHeders> requestProperty=new ArrayList<>();
	public PropertyObjectHttp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public List<RequestPropertyHeders> getRequestProperty() {
		return requestProperty;
	}
	public void setRequestProperty(List<RequestPropertyHeders> requestProperty) {
		this.requestProperty = requestProperty;
	}
	@Override
	public String toString() {
		return "PropertyObjectHttp [url=" + url + ", body=" + body + ", requestMethod=" + requestMethod
				+ ", requestProperty=" + requestProperty + "]";
	}

}
