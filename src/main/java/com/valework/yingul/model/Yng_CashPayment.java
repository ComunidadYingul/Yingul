package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_CashPayment {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cashPaymentId", nullable = false, updatable = false)
    private Long cashPaymentId;
	private String documentType;
	private String documentNumber;
	private String paymentMethod;
	
	public Yng_CashPayment(){}

	public Long getCashPaymentId() {
		return cashPaymentId;
	}

	public void setCashPaymentId(Long cashPaymentId) {
		this.cashPaymentId = cashPaymentId;
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

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
