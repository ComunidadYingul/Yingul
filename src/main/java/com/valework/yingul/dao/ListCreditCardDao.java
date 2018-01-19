package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_ListCreditCard;

public interface ListCreditCardDao extends CrudRepository<Yng_ListCreditCard, Long>{
	List<Yng_ListCreditCard> findAll();
	Yng_ListCreditCard findByListCreditCardId(Long listCreditCardId);

}
