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
public class Yng_Payment {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "paymentId", nullable = false, updatable = false)
    private Long paymentId;
	private String name;
	private String type;
	private String paymentPlan;
	private String status;
	private Long orderId;
	private String referenceCode;
	private String transactionId;
	private double value;
	private String currency;
	private String buyStatus;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "card_id")
    private Yng_Card yng_Card;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cashPayment_id")
    private Yng_CashPayment cashPayment;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "request_id")
    private Yng_Request yng_Request;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Yng_User user;
	
	public Yng_Payment() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaymentPlan() {
		return paymentPlan;
	}

	public void setPaymentPlan(String paymentPlan) {
		this.paymentPlan = paymentPlan;
	}

	public Yng_Card getYng_Card() {
		return yng_Card;
	}

	public void setYng_Card(Yng_Card yng_Card) {
		this.yng_Card = yng_Card;
	}

	public Yng_Request getYng_Request() {
		return yng_Request;
	}

	public void setYng_Request(Yng_Request yng_Request) {
		this.yng_Request = yng_Request;
	}

	public Yng_CashPayment getCashPayment() {
		return cashPayment;
	}

	public void setCashPayment(Yng_CashPayment cashPayment) {
		this.cashPayment = cashPayment;
	}

	public Yng_User getUser() {
		return user;
	}

	public void setUser(Yng_User user) {
		this.user = user;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(String buyStatus) {
		this.buyStatus = buyStatus;
	}

}
