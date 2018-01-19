package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_FindMotorized {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "findMotorizedId", nullable = false, updatable = false)
	private int findMotorizedId;
	private Long minPrice;
	private Long maxPrice;
	private Long rankPrice;
	private Long minYear;
	private Long categoryId;
	public Yng_FindMotorized() {
	}
	public int getFindMotorizedId() {
		return findMotorizedId;
	}
	public void setFindMotorizedId(int findMotorizedId) {
		this.findMotorizedId = findMotorizedId;
	}
	public Long getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}
	public Long getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Long maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Long getRankPrice() {
		return rankPrice;
	}
	public void setRankPrice(Long rankPrice) {
		this.rankPrice = rankPrice;
	}
	public Long getMinYear() {
		return minYear;
	}
	public void setMinYear(Long minYear) {
		this.minYear = minYear;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	@Override
	public String toString() {
		return "Yng_FindMotorized [findMotorizedId=" + findMotorizedId + ", minPrice=" + minPrice + ", maxPrice="
				+ maxPrice + ", rankPrice=" + rankPrice + ", minYear=" + minYear + ", categoryId=" + categoryId + "]";
	}

}
