package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Yng_XubioRequest {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "xubioRequestId", nullable = false, updatable = false)
    private long xubioRequestId;
	private String URI;
	private String info;
	@Column(columnDefinition = "text")
	private java.lang.String body="";
	private String date;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xubio_response_id")
    private Yng_XubioResponse xubioResponse;
	
	public Yng_XubioRequest() {
		super();
	}

	public long getXubioRequestId() {
		return xubioRequestId;
	}

	public void setXubioRequestId(long xubioRequestId) {
		this.xubioRequestId = xubioRequestId;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public java.lang.String getBody() {
		return body;
	}

	public void setBody(java.lang.String body) {
		this.body = body;
	}

	public Yng_XubioResponse getXubioResponse() {
		return xubioResponse;
	}

	public void setXubioResponse(Yng_XubioResponse xubioResponse) {
		this.xubioResponse = xubioResponse;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
