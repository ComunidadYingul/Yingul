package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_XubioSalesInvoice;
import com.valework.yingul.model.Yng_XubioTransaccionProductoItems;

public interface XubioTransaccionProductoItemsDao extends CrudRepository<Yng_XubioTransaccionProductoItems, Long>{
	List<Yng_XubioTransaccionProductoItems> findByXubioSalesInvoice(Yng_XubioSalesInvoice invoice);
}
