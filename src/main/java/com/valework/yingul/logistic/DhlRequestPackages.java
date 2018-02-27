package com.valework.yingul.logistic;

import java.util.ArrayList;
import java.util.List;

public class DhlRequestPackages {
		DhlRequestPackageDetails packageDetails =new  DhlRequestPackageDetails();
		DhlConsigneeAddress consigneeAddress=new DhlConsigneeAddress();
		List<DhlCustomsDetails> customsDetails = new ArrayList<DhlCustomsDetails>();
		DhlReturnAddress returnAddress=new DhlReturnAddress();
		public DhlRequestPackages() {
			super();
			// TODO Auto-generated constructor stub
		}
		public DhlRequestPackageDetails getPackageDetails() {
			return packageDetails;
		}
		public void setPackageDetails(DhlRequestPackageDetails packageDetails) {
			this.packageDetails = packageDetails;
		}
		public DhlConsigneeAddress getConsigneeAddress() {
			return consigneeAddress;
		}
		public void setConsigneeAddress(DhlConsigneeAddress consigneeAddress) {
			this.consigneeAddress = consigneeAddress;
		}
		public List<DhlCustomsDetails> getCustomsDetails() {
			return customsDetails;
		}
		public void setCustomsDetails(List<DhlCustomsDetails> customsDetails) {
			this.customsDetails = customsDetails;
		}
		public DhlReturnAddress getReturnAddress() {
			return returnAddress;
		}
		public void setReturnAddress(DhlReturnAddress returnAddress) {
			this.returnAddress = returnAddress;
		}
		@Override
		public String toString() {
			return "DhlRequestPackages [packageDetails=" + packageDetails + ", consigneeAddress=" + consigneeAddress
					+ ", customsDetails=" + customsDetails + ", returnAddress=" + returnAddress + "]";
		}
			
}
