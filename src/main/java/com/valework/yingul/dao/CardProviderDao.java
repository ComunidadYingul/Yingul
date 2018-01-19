package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_CardProvider;

public interface CardProviderDao extends CrudRepository<Yng_CardProvider, Long>{
	List<Yng_CardProvider> findAll();
	Yng_CardProvider findByCardProviderId(Long cardProviderId);
	
}
