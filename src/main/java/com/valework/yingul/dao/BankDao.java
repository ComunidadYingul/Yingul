package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Bank;

public interface BankDao extends CrudRepository<Yng_Bank, Long>{
	List<Yng_Bank> findByOrderByNameAsc();
	Yng_Bank findByBankId(Long bankId);
}

