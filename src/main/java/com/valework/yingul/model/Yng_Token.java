package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Yng_Token {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tokenId", nullable = false, updatable = false)
    private long tokenId;
	private String apiKey;
	private String secretKey;
	private String token;
	private String refresh_token;
	private String ubicationId;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Yng_User yng_User;

	
	
	public long getTokenId() {
		return tokenId;
	}
	public void setTokenId(long tokenId) {
		this.tokenId = tokenId;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getUbicationId() {
		return ubicationId;
	}
	public void setUbicationId(String ubicationId) {
		this.ubicationId = ubicationId;
	}
	public Yng_User getYng_User() {
		return yng_User;
	}
	public void setYng_User(Yng_User yng_User) {
		this.yng_User = yng_User;
	}
	@Override
	public String toString() {
		return "Yng_Token [tokenId=" + tokenId + ", apiKey=" + apiKey + ", secretKey=" + secretKey + ", token=" + token
				+ ", refresh_token=" + refresh_token + ", ubicationId=" + ubicationId + ", yng_User=" + yng_User + "]";
	}

		

	
	
	

}
