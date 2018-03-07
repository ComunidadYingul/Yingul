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
public class Yng_Shipment {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shipmentId", nullable = false, updatable = false)
    private Long shipmentId;
	private java.lang.String respuesta="";
	private String typeMail="";
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
	private Yng_Item yng_Item;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
	private Yng_User yng_User;
	private String ticket="";
	//numero para realizar el seguimiento si lo tiene
	private String shipmentCod="";
	public Yng_Shipment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}
	public java.lang.String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(java.lang.String respuesta) {
		this.respuesta = respuesta;
	}
	public String getTypeMail() {
		return typeMail;
	}
	public void setTypeMail(String typeMail) {
		this.typeMail = typeMail;
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
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getShipmentCod() {
		return shipmentCod;
	}
	public void setShipmentCod(String shipmentCod) {
		this.shipmentCod = shipmentCod;
	}
	@Override
	public String toString() {
		return "Yng_Shipment [shipmentId=" + shipmentId + ", respuesta=" + respuesta + ", typeMail=" + typeMail
				+ ", yng_Item=" + yng_Item + ", yng_User=" + yng_User + ", ticket=" + ticket + ", shipmentCod="
				+ shipmentCod + "]";
	}
	
	
}
