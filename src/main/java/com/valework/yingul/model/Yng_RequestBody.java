package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Yng_RequestBody {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestBodyId;
	@Column(columnDefinition = "text")
	private java.lang.String key="";
	@Column(columnDefinition = "text")
	private java.lang.String value="";
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id")
    private Yng_Request request;
	public Yng_RequestBody() {
	}
	public long getRequestBodyId() {
		return requestBodyId;
	}
	public void setRequestBodyId(long requestBodyId) {
		this.requestBodyId = requestBodyId;
	}
	public Yng_Request getRequest() {
		return request;
	}
	public void setRequest(Yng_Request request) {
		this.request = request;
	}
	public java.lang.String getKey() {
		return key;
	}
	public void setKey(java.lang.String key) {
		this.key = key;
	}
	public java.lang.String getValue() {
		return value;
	}
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
}
