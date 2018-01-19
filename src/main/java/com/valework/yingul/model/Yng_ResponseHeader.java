package com.valework.yingul.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
public class Yng_ResponseHeader {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long responseHeaderId;
	@Size(max = 500)
	private java.lang.String name;
	@Size(max = 500)
	private java.lang.String value;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "response_id")
    private Yng_Response response;
	
	public Yng_ResponseHeader() {
	}
	public long getResponseHeaderId() {
		return responseHeaderId;
	}
	public void setResponseHeaderId(long responseHeaderId) {
		this.responseHeaderId = responseHeaderId;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getValue() {
		return value;
	}
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	public Yng_Response getResponse() {
		return response;
	}
	public void setResponse(Yng_Response response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return "Yng_ResponseHeader [responseHeaderId=" + responseHeaderId + ", name=" + name + ", value=" + value
				+ ", response=" + response + "]";
	}
	
}
