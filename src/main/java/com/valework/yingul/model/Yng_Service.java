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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Yng_Service {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "serviceId", nullable = false, updatable = false)
    private Long serviceId;
	private String emailService;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id")
    private Yng_Item yng_Item;
	
	@OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    private Set<Yng_ServiceProvince> cobertureZone = new HashSet<>();   
    public Yng_Service() {
    	
    }
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Set<Yng_ServiceProvince> getCobertureZone() {
		return cobertureZone;
	}
	public void setCobertureZone(Set<Yng_ServiceProvince> cobertureZone) {
		this.cobertureZone = cobertureZone;
	}
	public String getEmailService() {
		return emailService;
	}
	public void setEmailService(String emailService) {
		this.emailService = emailService;
	}
	public Yng_Item getYng_Item() {
		return yng_Item;
	}
	public void setYng_Item(Yng_Item yng_Item) {
		this.yng_Item = yng_Item;
	}
	/*@Override
	public String toString() {
		return "Yng_Service [serviceId=" + serviceId + ", emailService=" + emailService + ", yng_Item=" + yng_Item
				+ ", cobertureZone=" + cobertureZone + "]";
	}*/

}
