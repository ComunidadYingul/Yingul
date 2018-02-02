package com.valework.yingul.service;

import java.util.List;
import java.util.Set;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_User;

public interface ItemService {
	List<Yng_Item> findAll();
	Set<Yng_Item> findServices(List<Yng_Service> serviceList);
	Set<Yng_Item> findMotorized(List<Yng_Motorized> motorizedList);
	Set<Yng_Item> searchMotorized(List<Yng_Motorized> motorizedList,Long categoryId,Long minPrice, Long maxPrice, Long minYear, Long maxYear);
	Yng_Item findByItemId(Long id);
	Yng_Item createItem(Yng_Item item, Set<Yng_ItemCategory> itemCategory);
	void save (Yng_Item yng_item);
	List<Yng_Item> findByUser(Yng_User yng_User);
	boolean verifyItemByCategory(Long itemId, Long categoryId);
	Set<Yng_Item> findProperty(List<Yng_Property> propertyList);
	Set<Yng_Item> searchProperty(List<Yng_Property> propertyList,Long categoryId,Long cityId);
	List<Yng_Item> findByOrderByItemIdDesc();
}
