package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Transaction;
import com.valework.yingul.model.Yng_XubioProofOfPurchase;

public interface XubioProofOfPurchaseDao extends CrudRepository<Yng_XubioProofOfPurchase, Long>{
	Yng_XubioProofOfPurchase findByTransaction(Yng_Transaction transaction);
}
