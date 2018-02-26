package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Yng_WireTransfer {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wireTransferId", nullable = false, updatable = false)
    private Long wireTransferId;
	
	private String titularName;
	private String cuitCuil;
	private Long cuitCuilNumber;
	private String accountType;
	private Long cbu;
	private double amount;
	private String currency;
	
	@OneToOne
	private Yng_Bank bank;
	
	@OneToOne
	private Yng_Transaction transaction;

	public Long getWireTransferId() {
		return wireTransferId;
	}

	public void setWireTransferId(Long wireTransferId) {
		this.wireTransferId = wireTransferId;
	}

	public String getTitularName() {
		return titularName;
	}

	public void setTitularName(String titularName) {
		this.titularName = titularName;
	}

	public String getCuitCuil() {
		return cuitCuil;
	}

	public void setCuitCuil(String cuitCuil) {
		this.cuitCuil = cuitCuil;
	}

	public Long getCuitCuilNumber() {
		return cuitCuilNumber;
	}

	public void setCuitCuilNumber(Long cuitCuilNumber) {
		this.cuitCuilNumber = cuitCuilNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Long getCbu() {
		return cbu;
	}

	public void setCbu(Long cbu) {
		this.cbu = cbu;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Yng_Bank getBank() {
		return bank;
	}

	public void setBank(Yng_Bank bank) {
		this.bank = bank;
	}

	public Yng_Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Yng_Transaction transaction) {
		this.transaction = transaction;
	}
	
}
