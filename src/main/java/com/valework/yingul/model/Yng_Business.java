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
public class Yng_Business {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "businessId", nullable = false, updatable = false)
    private Long businessId;
	private String businessName;
	private String documentType;
	@Column(unique=true)
	private String documentNumber;
	private String contributorType;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private Yng_User user;
	
	public Yng_Business() {
		super();
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getContributorType() {
		return contributorType;
	}

	public void setContributorType(String contributorType) {
		this.contributorType = contributorType;
	}

	public Yng_User getUser() {
		return user;
	}

	public void setUser(Yng_User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Yng_Business [businessId=" + businessId + ", businessName=" + businessName + ", documentType="
				+ documentType + ", documentNumber=" + documentNumber + ", contributorType=" + contributorType
				+ ", user=" + user + "]";
	}

}
