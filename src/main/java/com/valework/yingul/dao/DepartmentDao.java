package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Department;

public interface DepartmentDao extends CrudRepository<Yng_Department, Long>{
	Yng_Department findByName(String name);
}
