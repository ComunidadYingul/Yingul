
package com.valework.yingul.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Yng_Province {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "provinceId", nullable = false, updatable = false)
    private int provinceId;
	private String name;
	private String codigo31662;
	@OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
    private Set<Yng_ServiceProvince> serviceProvince = new HashSet<>();
	
	public Yng_Province() {
		
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Yng_ServiceProvince> getServiceProvince() {
		return serviceProvince;
	}
	public void setServiceProvince(Set<Yng_ServiceProvince> serviceProvince) {
		this.serviceProvince = serviceProvince;
	}
	@Override
	public String toString() {
		return "Yng_Province [provinceId=" + provinceId + ", name=" + name + ", codigo31662=" + codigo31662
				+ ", serviceProvince=" + serviceProvince + "]";
	}
	public String getCodigo31662() {
		return codigo31662;
	}
	public void setCodigo31662(String codigo31662) {
		this.codigo31662 = codigo31662;
	}
	

	
}

