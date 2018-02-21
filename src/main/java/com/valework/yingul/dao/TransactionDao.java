package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Transaction;

public interface TransactionDao extends CrudRepository<Yng_Transaction, Long> {

}
