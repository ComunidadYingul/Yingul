package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Yng_PaymentMethod {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "paymentMethodId", nullable = false, updatable = false)
    private Long paymentMethodId;
	private String name;
	private String type;
	private String paymentPlan;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "card_id")
    private Yng_Card yng_Card;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "request_id")
    private Yng_Request yng_Request;
	
	public Yng_PaymentMethod() {
	}

	public Long getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(Long paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
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

	@Override
	public String toString() {
		return "Yng_PaymentMethod [paymentMethodId=" + paymentMethodId + ", name=" + name + ", type=" + type
				+ ", paymentPlan=" + paymentPlan + ", yng_Card=" + yng_Card + ", yng_Request=" + yng_Request + "]";
	}

}
