package com.valework.yingul.logistic;

import java.util.ArrayList;
import java.util.List;

public class DhlPackages {
	DhlConsigneeAddress consigneeAddress=new DhlConsigneeAddress();
	DhlReturnAddress returnAddress=new DhlReturnAddress();
	DhlpackageDetails packageDetails=new DhlpackageDetails();
	List<DhlCustomsDetails> customsDetails=new ArrayList<DhlCustomsDetails>();
	DhlResponseDetails responseDetails= new DhlResponseDetails();
	public DhlPackages() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DhlConsigneeAddress getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(DhlConsigneeAddress consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public DhlReturnAddress getReturnAddress() {
		return returnAddress;
	}
	public void setReturnAddress(DhlReturnAddress returnAddress) {
		this.returnAddress = returnAddress;
	}
	public DhlpackageDetails getPackageDetails() {
		return packageDetails;
	}
	public void setPackageDetails(DhlpackageDetails packageDetails) {
		this.packageDetails = packageDetails;
	}
	public List<DhlCustomsDetails> getCustomsDetails() {
		return customsDetails;
	}
	public void setCustomsDetails(List<DhlCustomsDetails> customsDetails) {
		this.customsDetails = customsDetails;
	}
	public DhlResponseDetails getResponseDetails() {
		return responseDetails;
	}
	public void setResponseDetails(DhlResponseDetails responseDetails) {
		this.responseDetails = responseDetails;
	}
	@Override
	public String toString() {
		return "DhlPackages [consigneeAddress=" + consigneeAddress + ", returnAddress=" + returnAddress
				+ ", packageDetails=" + packageDetails + ", customsDetails=" + customsDetails + ", responseDetails="
				+ responseDetails + "]";
	}
	
	
}
