package com.valework.yingul.logistic;

public class DhlCustomsDetails {
	String itemDescription="";
	String countryOfOrigin="";
	//¿?
	String hsCode="";
	int packagedQuantity =0;
	double itemValue= 0;
	//¿?
	String skuNumber="";
	@Override
	public String toString() {
		return "DhlCustomsDetails [itemDescription=" + itemDescription + ", countryOfOrigin=" + countryOfOrigin
				+ ", hsCode=" + hsCode + ", packagedQuantity=" + packagedQuantity + ", itemValue=" + itemValue
				+ ", skuNumber=" + skuNumber + "]";
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}
	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}
	public String getHsCode() {
		return hsCode;
	}
	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}
	public int getPackagedQuantity() {
		return packagedQuantity;
	}
	public void setPackagedQuantity(int packagedQuantity) {
		this.packagedQuantity = packagedQuantity;
	}
	public double getItemValue() {
		return itemValue;
	}
	public void setItemValue(double itemValue) {
		this.itemValue = itemValue;
	}
	public String getSkuNumber() {
		return skuNumber;
	}
	public void setSkuNumber(String skuNumber) {
		this.skuNumber = skuNumber;
	}
	public DhlCustomsDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
