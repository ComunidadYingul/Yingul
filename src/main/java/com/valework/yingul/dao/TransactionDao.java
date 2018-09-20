package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Account;
import com.valework.yingul.model.Yng_Transaction;

public interface TransactionDao extends CrudRepository<Yng_Transaction, Long> {
	List<Yng_Transaction> findByAccountOrderByTransactionIdDesc(Yng_Account account);
	List<Yng_Transaction> findByInvoiceStatus(String invoiceStatus);
}
