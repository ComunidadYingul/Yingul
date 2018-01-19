package com.valework.yingul.service;

import java.util.List;
import com.valework.yingul.model.Yng_Category;

public interface CategoryService {
	List<Yng_Category> findAll();
	List<Yng_Category> findByItemTypeAndLevel(String itemType, int level);
	List<Yng_Category> findByFatherId(Long father);
	List<Yng_Category> findByName(String name);
}
