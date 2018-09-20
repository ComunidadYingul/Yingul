package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Transaction;
import com.valework.yingul.model.Yng_TransactionDetail;

public interface TransactionDetailDao extends CrudRepository<Yng_TransactionDetail, Long> {
	Yng_TransactionDetail findByTransaction(Yng_Transaction transaction);
}
