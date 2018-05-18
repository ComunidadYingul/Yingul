package com.valework.yingul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.dao.PaymentDao;
import com.valework.yingul.model.Yng_Payment;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired 
	private PaymentDao paymentDao;
	
	@RequestMapping("/getPaymentById/{paymentId}")
    public Yng_Payment getItemTypeById(@PathVariable("paymentId") Long paymentId,@RequestHeader("Authorization") String authorization) {
		return paymentDao.findByPaymentId(paymentId);
    }
}
