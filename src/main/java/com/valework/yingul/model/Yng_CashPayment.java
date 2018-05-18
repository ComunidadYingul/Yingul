package com.valework.yingul.model;

import java.util.Date;

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
	private String URL_PAYMENT_RECEIPT_PDF;
	private String URL_PAYMENT_RECEIPT_HTML;
	@Column(columnDefinition = "text")
	private java.lang.String buyJson;
    private Date expiration;
	
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

	public String getURL_PAYMENT_RECEIPT_PDF() {
		return URL_PAYMENT_RECEIPT_PDF;
	}

	public void setURL_PAYMENT_RECEIPT_PDF(String uRL_PAYMENT_RECEIPT_PDF) {
		URL_PAYMENT_RECEIPT_PDF = uRL_PAYMENT_RECEIPT_PDF;
	}

	public String getURL_PAYMENT_RECEIPT_HTML() {
		return URL_PAYMENT_RECEIPT_HTML;
	}

	public void setURL_PAYMENT_RECEIPT_HTML(String uRL_PAYMENT_RECEIPT_HTML) {
		URL_PAYMENT_RECEIPT_HTML = uRL_PAYMENT_RECEIPT_HTML;
	}

	public java.lang.String getBuyJson() {
		return buyJson;
	}

	public void setBuyJson(java.lang.String buyJson) {
		this.buyJson = buyJson;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

}
