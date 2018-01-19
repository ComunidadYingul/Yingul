package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Yng_Ubication {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ubicationId", nullable = false, updatable = false)
    private Long ubicationId;
	private String latitud;
	private String longitud;
	private String street;
	private String number;
	private String postalCode;
	private String aditional;
	private String codAndreani="";
	@OneToOne
    private Yng_City yng_City;
	@OneToOne
    private Yng_Province yng_Province;
	@OneToOne
    private Yng_Department yng_Department;
	@OneToOne
	private Yng_Barrio yng_Barrio;
	
	public Long getUbicationId() {
		return ubicationId;
	}
	public void setUbicationId(Long ubicationId) {
		this.ubicationId = ubicationId;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getAditional() {
		return aditional;
	}
	public void setAditional(String aditional) {
		this.aditional = aditional;
	}
	public Yng_City getYng_City() {
		return yng_City;
	}
	public void setYng_City(Yng_City yng_City) {
		this.yng_City = yng_City;
	}
	public Yng_Province getYng_Province() {
		return yng_Province;
	}
	public void setYng_Province(Yng_Province yng_Province) {
		this.yng_Province = yng_Province;
	}
	public Yng_Department getYng_Department() {
		return yng_Department;
	}
	public void setYng_Department(Yng_Department yng_Department) {
		this.yng_Department = yng_Department;
	}
	public Yng_Barrio getYng_Barrio() {
		return yng_Barrio;
	}
	public void setYng_Barrio(Yng_Barrio yng_Barrio) {
		this.yng_Barrio = yng_Barrio;
	}
	public String getCodAndreani() {
		return codAndreani;
	}
	public void setCodAndreani(String codAndreani) {
		this.codAndreani = codAndreani;
	}
	@Override
	public String toString() {
		return "Yng_Ubication [ubicationId=" + ubicationId + ", latitud=" + latitud + ", longitud=" + longitud
				+ ", street=" + street + ", number=" + number + ", postalCode=" + postalCode + ", aditional="
				+ aditional + ", codAndreani=" + codAndreani + ", yng_City=" + yng_City + ", yng_Province="
				+ yng_Province + ", yng_Department=" + yng_Department + ", yng_Barrio=" + yng_Barrio + "]";
	}

	
	

	
}
