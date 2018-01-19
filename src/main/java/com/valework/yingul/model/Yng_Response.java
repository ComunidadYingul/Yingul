package com.valework.yingul.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Yng_Response {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long responseId;
	private String status;
	private String message;
	
	@OneToMany(mappedBy = "response", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    @JsonBackReference(value="responseHeader")
    private Set<Yng_ResponseHeader> responseHeader = new HashSet<>();
	
    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    @JsonBackReference(value="responseBody")
    private Set<Yng_ResponseBody> responseBody = new HashSet<>();
    
	public Yng_Response() {
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
	public Set<Yng_ResponseBody> getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(Set<Yng_ResponseBody> responseBody) {
		this.responseBody = responseBody;
	}
	public Set<Yng_ResponseHeader> getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(Set<Yng_ResponseHeader> responseHeader) {
		this.responseHeader = responseHeader;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Yng_Response [responseId=" + responseId + ", status=" + status + ", message=" + message
				+ ", responseHeader=" + responseHeader + ", responseBody=" + responseBody + "]";
	}
		
}
