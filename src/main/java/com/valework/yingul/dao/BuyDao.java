package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Buy;
import com.valework.yingul.model.Yng_User;

public interface BuyDao extends CrudRepository<Yng_Buy, Long>{
	List<Yng_Buy> findByUserOrderByBuyIdDesc(Yng_User user);
	List<Yng_Buy> findBySellerOrderByBuyIdDesc(Yng_User user);

}
