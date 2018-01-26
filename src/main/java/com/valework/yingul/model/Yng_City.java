
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
public class Yng_City {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cityId", nullable = false, updatable = false)
	private int cityId;
	private String name;
	private String codigopostal;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "province_id")
    private Yng_Province yng_Province;
	
	public Yng_City() {
	}
	public Yng_Province getYng_Province() {
		return yng_Province;
	}
	public void setYng_Province(Yng_Province yng_Province) {
		this.yng_Province = yng_Province;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCodigopostal() {
		return codigopostal;
	}
	public void setCodigopostal(String codigopostal) {
		this.codigopostal = codigopostal;
	}


	
}

