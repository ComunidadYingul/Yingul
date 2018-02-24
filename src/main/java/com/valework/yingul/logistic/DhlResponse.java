package com.valework.yingul.logistic;

import java.util.ArrayList;
import java.util.List;

public class DhlResponse {
	List<DhlShipments> shipments = new ArrayList<DhlShipments>();

	public DhlResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<DhlShipments> getShipments() {
		return shipments;
	}

	public void setShipments(List<DhlShipments> shipments) {
		this.shipments = shipments;
	}

	@Override
	public String toString() {
		return "DhlResponse [shipments=" + shipments + "]";
	}
	

}
