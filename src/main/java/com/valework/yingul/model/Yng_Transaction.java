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
public class Yng_Transaction {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transactionId", nullable = false, updatable = false)
    private Long transactionId;
	private double amount;
	private String currency;
	private int day;
	private int month;
	private int year;
	private int hour;
	private int minute;
	private int second;
	private String type;
	private String description;
	//datos de la ip de la transaccion
	private String ip;
	private String org;
	private String lat;
	private String lon;
	private String city;
	private String country;
	private String countryCode;
	private String regionName;
	private String zip;
	//mejora 
	private boolean isAYingulTransaction;
	private boolean isAWireTransfer;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Yng_Account account;
    
    public Yng_Transaction() {
    	
    }

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Yng_Account getAccount() {
		return account;
	}

	public void setAccount(Yng_Account account) {
		this.account = account;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public boolean isAYingulTransaction() {
		return isAYingulTransaction;
	}

	public void setAYingulTransaction(boolean isAYingulTransaction) {
		this.isAYingulTransaction = isAYingulTransaction;
	}

	public boolean isAWireTransfer() {
		return isAWireTransfer;
	}

	public void setAWireTransfer(boolean isAWireTransfer) {
		this.isAWireTransfer = isAWireTransfer;
	}

}
