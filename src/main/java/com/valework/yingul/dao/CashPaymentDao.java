package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_CashPayment;


public interface CashPaymentDao extends CrudRepository<Yng_CashPayment, Long> {
	Yng_CashPayment findByCashPaymentId(Long cashPaymentId);
}
