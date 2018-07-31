package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_OmittedWord;
import java.util.List;

public interface OmittedWordDao extends CrudRepository<Yng_OmittedWord, Long>{
	List<Yng_OmittedWord> findAll();
}
