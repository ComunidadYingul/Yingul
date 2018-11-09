package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Quote;

public interface QuoteDao extends CrudRepository<Yng_Quote, Long> {
	List<Yng_Quote> findAll();
}
