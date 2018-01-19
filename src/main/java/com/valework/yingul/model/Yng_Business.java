package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Yng_Business {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "businessId", nullable = false, updatable = false)
    private Long businessId;
	
	private String name;
	private String socialName;
	private String typeContri;
	private boolean isBusiness;
	private String address;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	private Yng_User yng_User;

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSocialName() {
		return socialName;
	}

	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}

	public String getTypeContri() {
		return typeContri;
	}

	public void setTypeContri(String typeContri) {
		this.typeContri = typeContri;
	}

	public boolean isBusiness() {
		return isBusiness;
	}

	public void setBusiness(boolean isBusiness) {
		this.isBusiness = isBusiness;
	}

	public Yng_User getYng_User() {
		return yng_User;
	}

	public void setYng_User(Yng_User yng_User) {
		this.yng_User = yng_User;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Yng_Business [businessId=" + businessId + ", name=" + name + ", socialName=" + socialName
				+ ", typeContri=" + typeContri + ", isBusiness=" + isBusiness + ", address=" + address + ", yng_User="
				+ yng_User + "]";
	}


	
	
	
}
