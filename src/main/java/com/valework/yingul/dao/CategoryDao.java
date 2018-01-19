package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Category;

public interface CategoryDao extends CrudRepository<Yng_Category, Long>{
	Yng_Category findByName(String name);
	Yng_Category findByCategoryId(Long categoryId);
	List<Yng_Category> findAll();
	List<Yng_Category> findByItemTypeAndLevel(String itemType, int level);
	List<Yng_Category> findByFatherId(Long father);
}

