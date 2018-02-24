package com.valework.yingul.logistic;

import java.util.ArrayList;
import java.util.List;

public class DhlShipments {
    private long shipmentsId;
	private String pickupAccount="";
	private String distributionCenter="";
	List<DhlPackages> packages=new ArrayList<DhlPackages>();
	public DhlShipments() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getShipmentsId() {
		return shipmentsId;
	}
	public void setShipmentsId(long shipmentsId) {
		this.shipmentsId = shipmentsId;
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
	public List<DhlPackages> getPackages() {
		return packages;
	}
	public void setPackages(List<DhlPackages> packages) {
		this.packages = packages;
	}
	@Override
	public String toString() {
		return "DhlShipments [shipmentsId=" + shipmentsId + ", pickupAccount=" + pickupAccount + ", distributionCenter="
				+ distributionCenter + ", packages=" + packages + "]";
	}
	

	
}
