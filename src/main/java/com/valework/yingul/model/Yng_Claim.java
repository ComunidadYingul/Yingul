package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Yng_Claim {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "claimId", nullable = false, updatable = false)
    private Long claimId;
	
	private String claimText;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "confirm_id")
    private Yng_Confirm confirm;
    
    public Yng_Claim() {
    	
    }

	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	public String getClaimText() {
		return claimText;
	}

	public void setClaimText(String claimText) {
		this.claimText = claimText;
	}

	public Yng_Confirm getConfirm() {
		return confirm;
	}

	public void setConfirm(Yng_Confirm confirm) {
		this.confirm = confirm;
	}	
	
}
