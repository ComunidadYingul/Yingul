package com.valework.yingul.logistic;

public class AndreaniProperty {
	String SOAPAction;
	String Host ;
	String xmlInput;
	String wsURL;
	public String getSOAPAction() {
		return SOAPAction;
	}
	public void setSOAPAction(String sOAPAction) {
		SOAPAction = sOAPAction;
	}
	public String getHost() {
		return Host;
	}
	public void setHost(String host) {
		Host = host;
	}
	public String getXmlInput() {
		return xmlInput;
	}
	public void setXmlInput(String xmlInput) {
		this.xmlInput = xmlInput;
	}
	public String getWsURL() {
		return wsURL;
	}
	public void setWsURL(String wsURL) {
		this.wsURL = wsURL;
	}
	@Override
	public String toString() {
		return "AndreaniProperty [SOAPAction=" + SOAPAction + ", Host=" + Host + ", xmlInput=" + xmlInput + ", wsURL="
				+ wsURL + "]";
	}
	public AndreaniProperty(String sOAPAction, String host, String xmlInput, String wsURL) {
		super();
		SOAPAction = sOAPAction;
		Host = host;
		this.xmlInput = xmlInput;
		this.wsURL = wsURL;
	}
	public AndreaniProperty() {
		super();
	}
	
	

}
