package com.valework.yingul.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="coberture_zone")
public class Yng_ServiceProvince {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long serviceProvinceId;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
	@JsonBackReference(value="service_id")
    private Yng_Service service;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_id")
    private Yng_Province province;
	public Yng_ServiceProvince(Yng_Service service, Yng_Province province) {
        this.service = service;
        this.province = province;
    }
	public Yng_ServiceProvince() {
	}
	public long getServiceProvinceId() {
		return serviceProvinceId;
	}
	public void setServiceProvinceId(long serviceProvinceId) {
		this.serviceProvinceId = serviceProvinceId;
	}
	public Yng_Service getService() {
		return service;
	}
	public void setService(Yng_Service service) {
		this.service = service;
	}
	public Yng_Province getProvince() {
		return province;
	}
	public void setProvince(Yng_Province province) {
		this.province = province;
	}
	/*@Override
	public String toString() {
		return "Yng_ServiceProvince [serviceProvinceId=" + serviceProvinceId + ", service=" + service + ", province="
				+ province + "]";
	}*/

	
}
