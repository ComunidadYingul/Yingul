package com.valework.yingul.logistic;

public class DhlReturnAddress {
	String name="";
	String companyName="";
	String address1="";
	String city="";
	String state="";
	String postalCode="";
	String country="";
	@Override
	public String toString() {
		return "DhlReturnAddress [name=" + name + ", companyName=" + companyName + ", address1=" + address1 + ", city="
				+ city + ", state=" + state + ", postalCode=" + postalCode + ", country=" + country + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public DhlReturnAddress() {
		super();
	} 
	

	
}
