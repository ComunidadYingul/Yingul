package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_CreditCardProvider;

public interface CreditCardProviderDao extends CrudRepository<Yng_CreditCardProvider, Long>{
	List<Yng_CreditCardProvider>findAll();
}
