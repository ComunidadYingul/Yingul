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
public class Yng_Confirm {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "confirmId", nullable = false, updatable = false)
    private Long confirmId;
	
	private boolean sellerConfirm;
	private int daySellerConfirm;
	private int monthSellerConfirm;
	private int yearSellerConfirm;
	
	private boolean buyerConfirm;
	private int dayBuyerConfirm;
	private int monthBuyerConfirm;
	private int yearBuyerConfirm;
	
	private int dayInitClaim;
	private int monthInitClaim;
	private int yearInitiClaim;
	
	private int dayEndClaim;
	private int monthEndClaim;
	private int yearEndClaim;
	
	private int codeConfirm;
	private String status;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private Yng_User seller;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer_id")
    private Yng_User buyer;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buy_id")
    private Yng_Buy buy;
    
    public Yng_Confirm() {
    	
    }

	public Long getConfirmId() {
		return confirmId;
	}

	public void setConfirmId(Long confirmId) {
		this.confirmId = confirmId;
	}

	public boolean isSellerConfirm() {
		return sellerConfirm;
	}

	public void setSellerConfirm(boolean sellerConfirm) {
		this.sellerConfirm = sellerConfirm;
	}

	public int getDaySellerConfirm() {
		return daySellerConfirm;
	}

	public void setDaySellerConfirm(int daySellerConfirm) {
		this.daySellerConfirm = daySellerConfirm;
	}

	public int getMonthSellerConfirm() {
		return monthSellerConfirm;
	}

	public void setMonthSellerConfirm(int monthSellerConfirm) {
		this.monthSellerConfirm = monthSellerConfirm;
	}

	public int getYearSellerConfirm() {
		return yearSellerConfirm;
	}

	public void setYearSellerConfirm(int yearSellerConfirm) {
		this.yearSellerConfirm = yearSellerConfirm;
	}

	public boolean isBuyerConfirm() {
		return buyerConfirm;
	}

	public void setBuyerConfirm(boolean buyerConfirm) {
		this.buyerConfirm = buyerConfirm;
	}

	public int getDayBuyerConfirm() {
		return dayBuyerConfirm;
	}

	public void setDayBuyerConfirm(int dayBuyerConfirm) {
		this.dayBuyerConfirm = dayBuyerConfirm;
	}

	public int getMonthBuyerConfirm() {
		return monthBuyerConfirm;
	}

	public void setMonthBuyerConfirm(int monthBuyerConfirm) {
		this.monthBuyerConfirm = monthBuyerConfirm;
	}

	public int getYearBuyerConfirm() {
		return yearBuyerConfirm;
	}

	public void setYearBuyerConfirm(int yearBuyerConfirm) {
		this.yearBuyerConfirm = yearBuyerConfirm;
	}

	public int getDayInitClaim() {
		return dayInitClaim;
	}

	public void setDayInitClaim(int dayInitClaim) {
		this.dayInitClaim = dayInitClaim;
	}

	public int getMonthInitClaim() {
		return monthInitClaim;
	}

	public void setMonthInitClaim(int monthInitClaim) {
		this.monthInitClaim = monthInitClaim;
	}

	public int getYearInitiClaim() {
		return yearInitiClaim;
	}

	public void setYearInitiClaim(int yearInitiClaim) {
		this.yearInitiClaim = yearInitiClaim;
	}

	public int getDayEndClaim() {
		return dayEndClaim;
	}

	public void setDayEndClaim(int dayEndClaim) {
		this.dayEndClaim = dayEndClaim;
	}

	public int getMonthEndClaim() {
		return monthEndClaim;
	}

	public void setMonthEndClaim(int monthEndClaim) {
		this.monthEndClaim = monthEndClaim;
	}

	public int getYearEndClaim() {
		return yearEndClaim;
	}

	public void setYearEndClaim(int yearEndClaim) {
		this.yearEndClaim = yearEndClaim;
	}

	public Yng_Buy getBuy() {
		return buy;
	}

	public void setBuy(Yng_Buy buy) {
		this.buy = buy;
	}

	public int getCodeConfirm() {
		return codeConfirm;
	}

	public void setCodeConfirm(int codeConfirm) {
		this.codeConfirm = codeConfirm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Yng_User getSeller() {
		return seller;
	}

	public void setSeller(Yng_User seller) {
		this.seller = seller;
	}

	public Yng_User getBuyer() {
		return buyer;
	}

	public void setBuyer(Yng_User buyer) {
		this.buyer = buyer;
	}

	@Override
	public String toString() {
		return "Yng_Confirm [confirmId=" + confirmId + ", sellerConfirm=" + sellerConfirm + ", daySellerConfirm="
				+ daySellerConfirm + ", monthSellerConfirm=" + monthSellerConfirm + ", yearSellerConfirm="
				+ yearSellerConfirm + ", buyerConfirm=" + buyerConfirm + ", dayBuyerConfirm=" + dayBuyerConfirm
				+ ", monthBuyerConfirm=" + monthBuyerConfirm + ", yearBuyerConfirm=" + yearBuyerConfirm
				+ ", dayInitClaim=" + dayInitClaim + ", monthInitClaim=" + monthInitClaim + ", yearInitiClaim="
				+ yearInitiClaim + ", dayEndClaim=" + dayEndClaim + ", monthEndClaim=" + monthEndClaim
				+ ", yearEndClaim=" + yearEndClaim + ", codeConfirm=" + codeConfirm + ", status=" + status + ", seller="
				+ seller + ", buyer=" + buyer + ", buy=" + buy + "]";
	}
	
}
