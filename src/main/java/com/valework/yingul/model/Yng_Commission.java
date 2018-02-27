package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_Commission {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commissionId", nullable = false, updatable = false)
    private long commissionId;
	private String toWho;
	private String why;
	private double percentage;
	private String condition;
	public long getCommissionId() {
		return commissionId;
	}
	public void setCommissionId(long commissionId) {
		this.commissionId = commissionId;
	}
	public String getToWho() {
		return toWho;
	}
	public void setToWho(String toWho) {
		this.toWho = toWho;
	}
	public String getWhy() {
		return why;
	}
	public void setWhy(String why) {
		this.why = why;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
}
