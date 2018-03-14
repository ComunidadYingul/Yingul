package com.valework.yingul.logistic;

import java.util.ArrayList;
import java.util.List;

public class DhlRequestShipments {
	List<DhlRequestPackages> packages=new ArrayList<DhlRequestPackages>();
	private String pickupAccount="";
	private String distributionCenter="";
	public DhlRequestShipments() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<DhlRequestPackages> getPackages() {
		return packages;
	}
	public void setPackages(List<DhlRequestPackages> packages) {
		this.packages = packages;
	}
	public String getPickupAccount() {
		return pickupAccount;
	}
	public void setPickupAccount(String pickupAccount) {
		this.pickupAccount = pickupAccount;
	}
	public String getDistributionCenter() {
		return distributionCenter;
	}
	public void setDistributionCenter(String distributionCenter) {
		this.distributionCenter = distributionCenter;
	}
	@Override
	public String toString() {
		return "DhlShipmentsRequest [packages=" + packages + ", pickupAccount=" + pickupAccount
				+ ", distributionCenter=" + distributionCenter + "]";
	}
}
