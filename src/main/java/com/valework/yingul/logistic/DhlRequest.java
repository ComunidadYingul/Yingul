package com.valework.yingul.logistic;

import java.util.ArrayList;
import java.util.List;

public class DhlRequest {
	List<DhlRequestShipments> shipments = new ArrayList<DhlRequestShipments>();

	public DhlRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<DhlRequestShipments> getShipments() {
		return shipments;
	}

	public void setShipments(List<DhlRequestShipments> shipments) {
		this.shipments = shipments;
	}

	@Override
	public String toString() {
		return "DhlRequest [shipments=" + shipments + "]";
	}
	
}
