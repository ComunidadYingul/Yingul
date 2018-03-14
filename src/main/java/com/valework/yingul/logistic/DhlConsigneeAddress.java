package com.valework.yingul.logistic;

public class DhlConsigneeAddress {
	String name="";
	String address1="";
	String address2="";
	String city="";
	String state="";
	String postalCode="";
	String country="";
	String email="";
	String phone="";
	public DhlConsigneeAddress() {
		super();
	}
	@Override
	public String toString() {
		return "DhlConsigneeAddress [name=" + name + ", address1=" + address1 + ", address2=" + address2 + ", city="
				+ city + ", state=" + state + ", postalCode=" + postalCode + ", country=" + country + ", email=" + email
				+ ", phone=" + phone + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}	
}
