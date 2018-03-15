package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Value;

@Entity
public class Yng_Claim {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "claimId", nullable = false, updatable = false)
    private Long claimId;
	
	private String claimText;
	@Value("${some.key:false}")
	private boolean change;
	@Value("${some.key:0}")
	private int codeChange;
	@Value("${some.key:false}")
	private boolean back;
	@Value("${some.key:0}")
	private int codeBack;
	@Value("${some.key:false}")
	private boolean minuse;
	@Value("${some.key:0}")
	private int codeMinuse;
	@Value("${some.key:pending}")
	private String status;
	
	
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

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public int getCodeChange() {
		return codeChange;
	}

	public void setCodeChange(int codeChange) {
		this.codeChange = codeChange;
	}

	public boolean isBack() {
		return back;
	}

	public void setBack(boolean back) {
		this.back = back;
	}

	public int getCodeBack() {
		return codeBack;
	}

	public void setCodeBack(int codeBack) {
		this.codeBack = codeBack;
	}

	public boolean isMinuse() {
		return minuse;
	}

	public void setMinuse(boolean minuse) {
		this.minuse = minuse;
	}

	public int getCodeMinuse() {
		return codeMinuse;
	}

	public void setCodeMinuse(int codeMinuse) {
		this.codeMinuse = codeMinuse;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
