package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Card;
import com.valework.yingul.model.Yng_User;

public interface CardService {
	List<Yng_Card> findByUser(Yng_User yng_User);
}
