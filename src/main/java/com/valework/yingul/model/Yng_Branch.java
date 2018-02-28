package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Yng_Branch {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "branchId", nullable = false, updatable = false)
    private Long branchId;
	private String nameMail="";
	private String street="";
	private String ubicacion="";
	private String schedules="";
	private String dateDelivery="";
	private java.lang.String respuesta="";
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "envio_id")
	private Yng_Envio yng_Envio;
	public Yng_Branch() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getNameMail() {
		return nameMail;
	}
	public void setNameMail(String nameMail) {
		this.nameMail = nameMail;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getSchedules() {
		return schedules;
	}
	public void setSchedules(String schedules) {
		this.schedules = schedules;
	}
	public String getDateDelivery() {
		return dateDelivery;
	}
	public void setDateDelivery(String dateDelivery) {
		this.dateDelivery = dateDelivery;
	}
	public java.lang.String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(java.lang.String respuesta) {
		this.respuesta = respuesta;
	}
	public Yng_Envio getYng_Envio() {
		return yng_Envio;
	}
	public void setYng_Envio(Yng_Envio yng_Envio) {
		this.yng_Envio = yng_Envio;
	}
	@Override
	public String toString() {
		return "Yng_Branch [branchId=" + branchId + ", nameMail=" + nameMail + ", street=" + street + ", ubicacion="
				+ ubicacion + ", schedules=" + schedules + ", dateDelivery=" + dateDelivery + ", respuesta=" + respuesta
				+ ", yng_Envio=" + yng_Envio + "]";
	}
	
}
