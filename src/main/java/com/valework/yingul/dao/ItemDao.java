package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Item;

public interface ItemDao extends CrudRepository<Yng_Item, Long> {
	Yng_Item findByItemId(Long id);
	//List<Yng_Item> findAll();
	List<Yng_Item> findByOrderByItemIdDesc();
	List<Yng_Item> findByIsOverOrderByItemIdDesc(boolean sw);
}
