package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_CardProvider;
import com.valework.yingul.model.Yng_ListCreditCard;

public interface CreditCardProviderService {
	List<Yng_CardProvider> findByListCreditCard(Yng_ListCreditCard yng_ListCreditCard);
}
