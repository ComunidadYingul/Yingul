package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Value;

@Entity
public class Yng_TransactionDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transactionDetailId", nullable = false, updatable = false)
    private Long transactionDetail;
	@Value("${some.key:0}")
	private double costTotal;
	@Value("${some.key:0}")
	private double costPAYU;
	@Value("${some.key:0}")
	private double costCommission;
	
	@OneToOne
	private Yng_Transaction transaction;

    public Yng_TransactionDetail() {
    	
    }

	public Long getTransactionDetail() {
		return transactionDetail;
	}

	public void setTransactionDetail(Long transactionDetail) {
		this.transactionDetail = transactionDetail;
	}

	public double getCostTotal() {
		return costTotal;
	}

	public void setCostTotal(double costTotal) {
		this.costTotal = costTotal;
	}

	public double getCostPAYU() {
		return costPAYU;
	}

	public void setCostPAYU(double costPAYU) {
		this.costPAYU = costPAYU;
	}

	public double getCostCommission() {
		return costCommission;
	}

	public void setCostCommission(double costCommission) {
		this.costCommission = costCommission;
	}

	public Yng_Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Yng_Transaction transaction) {
		this.transaction = transaction;
	}
 
}
