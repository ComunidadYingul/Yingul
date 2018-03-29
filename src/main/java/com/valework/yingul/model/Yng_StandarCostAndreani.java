package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_StandarCostAndreani {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "standarCostAndreaniId", nullable = false, updatable = false)
    private int standarCostAndreaniId;
	
	private int weightFrom; 
	private int weightUp; 
	private double	rateBranch;
	private double	rateURGENT;
	private double	rateHome;
	public Yng_StandarCostAndreani() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getStandarCostAndreaniId() {
		return standarCostAndreaniId;
	}
	public void setStandarCostAndreaniId(int standarCostAndreaniId) {
		this.standarCostAndreaniId = standarCostAndreaniId;
	}
	public int getWeightFrom() {
		return weightFrom;
	}
	public void setWeightFrom(int weightFrom) {
		this.weightFrom = weightFrom;
	}
	public int getWeightUp() {
		return weightUp;
	}
	public void setWeightUp(int weightUp) {
		this.weightUp = weightUp;
	}
	public double getRateBranch() {
		return rateBranch;
	}
	public void setRateBranch(double rateBranch) {
		this.rateBranch = rateBranch;
	}
	public double getRateURGENT() {
		return rateURGENT;
	}
	public void setRateURGENT(double rateURGENT) {
		this.rateURGENT = rateURGENT;
	}
	public double getRateHome() {
		return rateHome;
	}
	public void setRateHome(double rateHome) {
		this.rateHome = rateHome;
	}
	@Override
	public String toString() {
		return "Yng_StandarCostAndreani [standarCostAndreaniId=" + standarCostAndreaniId + ", weightFrom=" + weightFrom
				+ ", weightUp=" + weightUp + ", rateBranch=" + rateBranch + ", rateURGENT=" + rateURGENT + ", rateHome="
				+ rateHome + "]";
	}

}
