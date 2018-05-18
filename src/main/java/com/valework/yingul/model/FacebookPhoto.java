package com.valework.yingul.model;

public class FacebookPhoto {
	private String message="";
	private String access_token="";
	private String url="";
	public FacebookPhoto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "FaceBook [message=" + message + ", access_token=" + access_token + ", url=" + url + "]";
	}	
}
