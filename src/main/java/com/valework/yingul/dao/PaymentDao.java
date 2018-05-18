package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Payment;

public interface PaymentDao extends CrudRepository<Yng_Payment, Long>{
	Yng_Payment findByPaymentId(Long paymentId);
	List<Yng_Payment> findByTypeAndStatusAndBuyStatus(String type, String status, String buyStatus);
}
