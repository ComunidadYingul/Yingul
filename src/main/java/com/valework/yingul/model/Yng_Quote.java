package com.valework.yingul.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Yng_Quote {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long quoteId;
	private double rate;
	private double rateOrigin;
	private java.lang.String respuesta="";
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
	private Yng_Item yng_Item;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
	private Yng_User yng_User;	 
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id")
	private Yng_Branch yng_Branch;
	public Yng_Quote() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(long quoteId) {
		this.quoteId = quoteId;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getRateOrigin() {
		return rateOrigin;
	}
	public void setRateOrigin(double rateOrigin) {
		this.rateOrigin = rateOrigin;
	}
	public java.lang.String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(java.lang.String respuesta) {
		this.respuesta = respuesta;
	}
	public Yng_Item getYng_Item() {
		return yng_Item;
	}
	public void setYng_Item(Yng_Item yng_Item) {
		this.yng_Item = yng_Item;
	}
	public Yng_User getYng_User() {
		return yng_User;
	}
	public void setYng_User(Yng_User yng_User) {
		this.yng_User = yng_User;
	}
	public Yng_Branch getYng_Branch() {
		return yng_Branch;
	}
	public void setYng_Branch(Yng_Branch yng_Branch) {
		this.yng_Branch = yng_Branch;
	}
	@Override
	public String toString() {
		return "Yng_Quote [quoteId=" + quoteId + ", rate=" + rate + ", rateOrigin=" + rateOrigin + ", respuesta="
				+ respuesta + ", yng_Item=" + yng_Item + ", yng_User=" + yng_User + ", yng_Branch=" + yng_Branch + "]";
	}	
}
