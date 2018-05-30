package com.valework.yingul.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Yng_ListCreditCard {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "listCreditCardId", nullable = false, updatable = false)
    private Long listCreditCardId;
	private String name;
	private String keyPayu;
	
    public Yng_ListCreditCard() {}

	public Long getListCreditCardId() {
		return listCreditCardId;
	}

	public void setListCreditCardId(Long listCreditCardId) {
		this.listCreditCardId = listCreditCardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeyPayu() {
		return keyPayu;
	}

	public void setKeyPayu(String keyPayu) {
		this.keyPayu = keyPayu;
	}

}
