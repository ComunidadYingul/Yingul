package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_ShippingQuote {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shippingQuoteId", nullable = false, updatable = false)
    private Long shippingQuoteId;
	private double rate=0;
	private java.lang.String respuesta="";
	public Yng_ShippingQuote() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getShippingQuoteId() {
		return shippingQuoteId;
	}
	public void setShippingQuoteId(Long shippingQuoteId) {
		this.shippingQuoteId = shippingQuoteId;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public java.lang.String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(java.lang.String respuesta) {
		this.respuesta = respuesta;
	}
	@Override
	public String toString() {
		return "Yng_ShippingQuote [shippingQuoteId=" + shippingQuoteId + ", rate=" + rate + ", respuesta=" + respuesta
				+ "]";
	}
		
}
