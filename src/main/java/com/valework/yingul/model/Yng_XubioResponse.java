package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_XubioResponse {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long responseId;
	private String status;
	@Column(columnDefinition = "text")
	private java.lang.String message;
	@Column(columnDefinition = "text")
	private java.lang.String header="";
	@Column(columnDefinition = "text")
	private java.lang.String body="";
    
	public Yng_XubioResponse() {
		super();
	}

	public long getResponseId() {
		return responseId;
	}

	public void setResponseId(long responseId) {
		this.responseId = responseId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public java.lang.String getMessage() {
		return message;
	}

	public void setMessage(java.lang.String message) {
		this.message = message;
	}

	public java.lang.String getHeader() {
		return header;
	}

	public void setHeader(java.lang.String header) {
		this.header = header;
	}

	public java.lang.String getBody() {
		return body;
	}

	public void setBody(java.lang.String body) {
		this.body = body;
	}
	
		
}
