
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
public class Yng_Country {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "countryId", nullable = false, updatable = false)
	private int countryId;
	private String name;
	private String zip;
	private boolean toBuy;
	private boolean toSell;
	private String countryCod="";
	private String currency="";
	public Yng_Country() {
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public boolean isToBuy() {
		return toBuy;
	}
	public void setToBuy(boolean toBuy) {
		this.toBuy = toBuy;
	}
	public boolean isToSell() {
		return toSell;
	}
	public void setToSell(boolean toSell) {
		this.toSell = toSell;
	}
	public String getCountryCod() {
		return countryCod;
	}
	public void setCountryCod(String countryCod) {
		this.countryCod = countryCod;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Override
	public String toString() {
		return "Yng_Country [countryId=" + countryId + ", name=" + name + ", zip=" + zip + ", toBuy=" + toBuy
				+ ", toSell=" + toSell + ", countryCod=" + countryCod + ", currency=" + currency + "]";
	}

}

