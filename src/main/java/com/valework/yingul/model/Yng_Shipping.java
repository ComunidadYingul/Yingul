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
public class Yng_Shipping {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shippingId", nullable = false, updatable = false)
    private Long shippingId;
	private String typeShipping="";
	private boolean dhl=false;
	private boolean fedex=false;
	private boolean andreani=false;
	private String shippingStatus="";
	private String nameContact="";
	private String phoneContact="";
	//crear desde dhl hasta shipment de manera manual 
	
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quote_id")
    private Yng_Quote yng_Quote=new Yng_Quote();
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipment_id")
    private Yng_Shipment yng_Shipment=new Yng_Shipment();

	public Yng_Shipping() {
		super();
	}

	public Long getShippingId() {
		return shippingId;
	}

	public void setShippingId(Long shippingId) {
		this.shippingId = shippingId;
	}

	public String getTypeShipping() {
		return typeShipping;
	}

	public void setTypeShipping(String typeShipping) {
		this.typeShipping = typeShipping;
	}

	public boolean isDhl() {
		return dhl;
	}

	public void setDhl(boolean dhl) {
		this.dhl = dhl;
	}

	public boolean isFedex() {
		return fedex;
	}

	public void setFedex(boolean fedex) {
		this.fedex = fedex;
	}

	public boolean isAndreani() {
		return andreani;
	}

	public void setAndreani(boolean andreani) {
		this.andreani = andreani;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Yng_Quote getYng_Quote() {
		return yng_Quote;
	}

	public void setYng_Quote(Yng_Quote yng_Quote) {
		this.yng_Quote = yng_Quote;
	}

	public Yng_Shipment getYng_Shipment() {
		return yng_Shipment;
	}

	public void setYng_Shipment(Yng_Shipment yng_Shipment) {
		this.yng_Shipment = yng_Shipment;
	}

	public String getNameContact() {
		return nameContact;
	}

	public void setNameContact(String nameContact) {
		this.nameContact = nameContact;
	}

	public String getPhoneContact() {
		return phoneContact;
	}

	public void setPhoneContact(String phoneContact) {
		this.phoneContact = phoneContact;
	}

	@Override
	public String toString() {
		return "Yng_Shipping [shippingId=" + shippingId + ", typeShipping=" + typeShipping + ", dhl=" + dhl + ", fedex="
				+ fedex + ", andreani=" + andreani + ", shippingStatus=" + shippingStatus + ", nameContact="
				+ nameContact + ", phoneContact=" + phoneContact + ", yng_Quote=" + yng_Quote + ", yng_Shipment="
				+ yng_Shipment + "]";
	}


	
}
