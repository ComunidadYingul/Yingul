package com.valework.yingul.logistic;

public class AccessTokenDHL {
	private String AccessTokenId="";
    private String access_token="";
    private String token_type="";
    private int expires_in=0;
    private String scope="";
	public AccessTokenDHL(String accessTokenId, String access_token, String token_type, int expires_in, String scope) {
		super();
		AccessTokenId = accessTokenId;
		this.access_token = access_token;
		this.token_type = token_type;
		this.expires_in = expires_in;
		this.scope = scope;
	}
	public AccessTokenDHL() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAccessTokenId() {
		return AccessTokenId;
	}
	public void setAccessTokenId(String accessTokenId) {
		AccessTokenId = accessTokenId;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	@Override
	public String toString() {
		return "AccessTokenDHL [AccessTokenId=" + AccessTokenId + ", access_token=" + access_token + ", token_type="
				+ token_type + ", expires_in=" + expires_in + ", scope=" + scope + "]";
	}
	
    
}
