package com.valework.yingul.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Yng_Request {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestId;
	private String URI;
	private String info;
	
	@OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    @JsonBackReference(value="requestBody")
    private Set<Yng_RequestBody> requestBody = new HashSet<>();
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "response_id")
    private Yng_Response yng_Response;
	
	public Yng_Request() {
	}
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
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
	public Set<Yng_RequestBody> getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(Set<Yng_RequestBody> requestBody) {
		this.requestBody = requestBody;
	}
	public Yng_Response getYng_Response() {
		return yng_Response;
	}
	public void setYng_Response(Yng_Response yng_Response) {
		this.yng_Response = yng_Response;
	}
	@Override
	public String toString() {
		return "Yng_Request [requestId=" + requestId + ", URI=" + URI + ", info=" + info + ", requestBody="
				+ requestBody + ", yng_Response=" + yng_Response + "]";
	}
}
