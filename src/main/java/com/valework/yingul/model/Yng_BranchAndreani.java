package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_BranchAndreani {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "branchAndreaniId", nullable = false, updatable = false)
    private Long branchAndreaniId;
	private String codAndreani;
	private String street="";
	private String location="";
	private String schedules="";
	private String dateDelivery="";
	private String phones="";
	private String sucursal="";
	public Yng_BranchAndreani() {
		super();
	}
	public Long getBranchAndreaniId() {
		return branchAndreaniId;
	}
	public void setBranchAndreaniId(Long branchAndreaniId) {
		this.branchAndreaniId = branchAndreaniId;
	}
	public String getCodAndreani() {
		return codAndreani;
	}
	public void setCodAndreani(String codAndreani) {
		this.codAndreani = codAndreani;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	@Override
	public String toString() {
		return "Yng_BranchAndreani [branchAndreaniId=" + branchAndreaniId + ", codAndreani=" + codAndreani + ", street="
				+ street + ", location=" + location + ", schedules=" + schedules + ", dateDelivery=" + dateDelivery
				+ ", phones=" + phones + ", sucursal=" + sucursal + "]";
	}	
}
