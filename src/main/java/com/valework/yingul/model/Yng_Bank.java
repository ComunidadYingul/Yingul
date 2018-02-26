package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_Bank {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bankId", nullable = false, updatable = false)
    private Long bankId;
	private String name;
    
	public Yng_Bank() {	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
