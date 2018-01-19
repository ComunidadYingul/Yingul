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
public class Yng_Barrio {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "barrioId", nullable = false, updatable = false)
	private int barrioId;
	private String name;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id")
    private Yng_City yng_City;
	public int getBarrioId() {
		return barrioId;
	}
	public void setBarrioId(int barrioId) {
		this.barrioId = barrioId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Yng_City getYng_City() {
		return yng_City;
	}
	public void setYng_City(Yng_City yng_City) {
		this.yng_City = yng_City;
	}
	@Override
	public String toString() {
		return "Yng_Barrio [barrioId=" + barrioId + ", name=" + name + ", yng_City=" + yng_City + "]";
	}
	
}
