package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;

public interface ItemCategoryDao extends CrudRepository<Yng_ItemCategory, Long>{
	List<Yng_ItemCategory> findAll();
	List<Yng_ItemCategory> findByOrderByCategoryDesc();
	List<Yng_ItemCategory> findByItem(Yng_Item item);
}
