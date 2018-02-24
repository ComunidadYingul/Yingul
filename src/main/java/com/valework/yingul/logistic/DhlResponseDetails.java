package com.valework.yingul.logistic;

import java.util.ArrayList;
import java.util.List;

public class DhlResponseDetails {	
	List<DhlLabelDetails> labelDetails= new ArrayList<DhlLabelDetails>();
	String trackingNumber ="";
	@Override
	public String toString() {
		return "DhlResponseDetails [labelDetails=" + labelDetails + ", trackingNumber=" + trackingNumber + "]";
	}
	public List<DhlLabelDetails> getLabelDetails() {
		return labelDetails;
	}
	public void setLabelDetails(List<DhlLabelDetails> labelDetails) {
		this.labelDetails = labelDetails;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public DhlResponseDetails() {
		super();
		// TODO Auto-generated constructor stub
	}	
}
