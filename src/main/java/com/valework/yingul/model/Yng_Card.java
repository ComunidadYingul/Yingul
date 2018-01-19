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
public class Yng_Card {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cardId", nullable = false, updatable = false)
    private Long cardId;
	private Long number;
	private int dueMonth;
	private int dueYear;
	private String fullName;
	private Long dni;
	private int securityCode;
	private String type;
	private String provider;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cardProvider_id")
    private Yng_CardProvider yng_CardProvider;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Yng_User user;
	
	private Yng_Card() {}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public int getDueMonth() {
		return dueMonth;
	}
	public void setDueMonth(int dueMonth) {
		this.dueMonth = dueMonth;
	}
	public int getDueYear() {
		return dueYear;
	}
	public void setDueYear(int dueYear) {
		this.dueYear = dueYear;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Long getDni() {
		return dni;
	}
	public void setDni(Long dni) {
		this.dni = dni;
	}
	public int getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(int securityCode) {
		this.securityCode = securityCode;
	}
	public Yng_CardProvider getYng_CardProvider() {
		return yng_CardProvider;
	}
	public void setYng_CardProvider(Yng_CardProvider yng_CardProvider) {
		this.yng_CardProvider = yng_CardProvider;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Yng_User getUser() {
		return user;
	}
	public void setUser(Yng_User user) {
		this.user = user;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	@Override
	public String toString() {
		return "Yng_Card [cardId=" + cardId + ", number=" + number + ", dueMonth=" + dueMonth + ", dueYear=" + dueYear
				+ ", fullName=" + fullName + ", dni=" + dni + ", securityCode=" + securityCode + ", type=" + type
				+ ", provider=" + provider + ", yng_CardProvider=" + yng_CardProvider + ", user=" + user + "]";
	}
	
}
