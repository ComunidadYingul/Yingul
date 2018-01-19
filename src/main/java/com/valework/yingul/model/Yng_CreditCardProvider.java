package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="creditCard_provider")
public class Yng_CreditCardProvider {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "creditCardProviderId", nullable = false, updatable = false)
    private long creditCardProviderId;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "listCreditCardId")
    private Yng_ListCreditCard listCreditCard;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cardProviderId")
    private Yng_CardProvider cardProvider;
	
	public Yng_CreditCardProvider(Yng_ListCreditCard listCreditCard, Yng_CardProvider cardProvider) {
        this.listCreditCard = listCreditCard;
        this.cardProvider = cardProvider;
    }
	
	public Yng_CreditCardProvider() {
		
	}

	public long getCreditCardProviderId() {
		return creditCardProviderId;
	}

	public void setCreditCardProviderId(long creditCardProviderId) {
		this.creditCardProviderId = creditCardProviderId;
	}

	public Yng_ListCreditCard getListCreditCard() {
		return listCreditCard;
	}

	public void setListCreditCard(Yng_ListCreditCard listCreditCard) {
		this.listCreditCard = listCreditCard;
	}

	public Yng_CardProvider getCardProvider() {
		return cardProvider;
	}

	public void setCardProvider(Yng_CardProvider cardProvider) {
		this.cardProvider = cardProvider;
	}

	@Override
	public String toString() {
		return "Yng_CreditCardProvider [creditCardProviderId=" + creditCardProviderId + ", listCreditCard="
				+ listCreditCard + ", cardProvider=" + cardProvider + "]";
	}
	
}
