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
public class Yng_ResponseBody {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long responseBodyId;
	private java.lang.String key;
	private java.lang.String value;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "response_id")
    private Yng_Response response;
	public Yng_ResponseBody() {
	}
	public long getResponseBodyId() {
		return responseBodyId;
	}
	public void setResponseBodyId(long responseBodyId) {
		this.responseBodyId = responseBodyId;
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
	public Yng_Response getResponse() {
		return response;
	}
	public void setResponse(Yng_Response response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return "Yng_ResponseBody [responseBodyId=" + responseBodyId + ", key=" + key + ", value=" + value
				+ ", response=" + response + "]";
	}
	
}
