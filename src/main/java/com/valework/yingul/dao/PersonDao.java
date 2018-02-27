package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_User;

public interface PersonDao extends CrudRepository<Yng_Person, Long>{
	List<Yng_Person> findAll();

}
