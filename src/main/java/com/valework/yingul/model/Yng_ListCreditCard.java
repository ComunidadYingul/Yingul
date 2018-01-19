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
	
	@OneToMany(mappedBy = "listCreditCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
	@JsonBackReference(value="creditCardProvider")
    private Set<Yng_CreditCardProvider> creditCardProvider = new HashSet<>();
	
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

	public Set<Yng_CreditCardProvider> getCreditCardProvider() {
		return creditCardProvider;
	}

	public void setCreditCardProvider(Set<Yng_CreditCardProvider> creditCardProvider) {
		this.creditCardProvider = creditCardProvider;
	}

	@Override
	public String toString() {
		return "Yng_ListCreditCard [listCreditCardId=" + listCreditCardId + ", name=" + name + ", creditCardProvider="
				+ creditCardProvider + "]";
	}
    

}
