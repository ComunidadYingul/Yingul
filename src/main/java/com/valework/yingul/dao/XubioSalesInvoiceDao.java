package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.model.Yng_XubioSalesInvoice;

public interface XubioSalesInvoiceDao extends CrudRepository<Yng_XubioSalesInvoice, Long>{
	List<Yng_XubioSalesInvoice> findAll();
	List<Yng_XubioSalesInvoice> findByConfirm(Yng_Confirm confirm);
}
