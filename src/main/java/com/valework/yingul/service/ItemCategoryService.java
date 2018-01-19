package com.valework.yingul.service;

import java.util.List;
import com.valework.yingul.model.Yng_Category;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;

public interface ItemCategoryService {
	List<Yng_ItemCategory> findByItem(Yng_Item yng_Item);
	List<Yng_ItemCategory> findByCategory(Yng_Category yng_Category);
}
