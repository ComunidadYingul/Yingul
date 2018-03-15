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
public class Yng_Buy {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "buyId", nullable = false, updatable = false)
    private Long buyId;
	private double cost;
	private double shippingCost;
	private double itemCost;
	private String money;
	private int quantity;
	//datos de la compra obtenidos con la ip de comprador
	private String ip;
	private String org;
	private String lat;
	private String lon;
	private String city;
	private String country;
	private String countryCode;
	private String regionName;
	private String zip;
	private String time;
	private String userAgent;
	private String cookie;
	private String deviceSessionId;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Yng_User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private Yng_User seller;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Yng_Item yng_item;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private Yng_Payment yng_Payment;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_id")
    private Yng_Shipping shipping;

	public Long getBuyId() {
		return buyId;
	}

	public void setBuyId(Long buyId) {
		this.buyId = buyId;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Yng_User getUser() {
		return user;
	}

	public void setUser(Yng_User user) {
		this.user = user;
	}

	public Yng_Item getYng_item() {
		return yng_item;
	}

	public void setYng_item(Yng_Item yng_item) {
		this.yng_item = yng_item;
	}
	
	public Yng_Payment getYng_Payment() {
		return yng_Payment;
	}

	public void setYng_Payment(Yng_Payment yng_Payment) {
		this.yng_Payment = yng_Payment;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Yng_Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Yng_Shipping shipping) {
		this.shipping = shipping;
	}

	public double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public double getItemCost() {
		return itemCost;
	}

	public void setItemCost(double itemCost) {
		this.itemCost = itemCost;
	}
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getDeviceSessionId() {
		return deviceSessionId;
	}

	public void setDeviceSessionId(String deviceSessionId) {
		this.deviceSessionId = deviceSessionId;
	}

	public Yng_User getSeller() {
		return seller;
	}

	public void setSeller(Yng_User seller) {
		this.seller = seller;
	}
	
	
	
}
