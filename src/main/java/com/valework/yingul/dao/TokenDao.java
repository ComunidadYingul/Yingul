package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Token;

public interface TokenDao extends CrudRepository<Yng_Token, Long>{
	List<Yng_Token> findAll();


}
