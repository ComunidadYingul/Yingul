package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.valework.yingul.dao.ItemCategoryDao;
import com.valework.yingul.model.Yng_Category;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.service.ItemCategoryService;

@Service
@Transactional
public class ItemCategoryServiceImpl implements ItemCategoryService{
	
	@Autowired
	ItemCategoryDao itemCategoryDao;

	public List<Yng_ItemCategory> findByItem(Yng_Item yng_Item) {
		Long itemId = yng_Item.getItemId();
		List<Yng_ItemCategory> itemCategoryList = itemCategoryDao.findAll().stream() 			//convert list to stream
                .filter(itemCategory -> itemId==itemCategory.getItem().getItemId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return itemCategoryList;
	}

	public List<Yng_ItemCategory> findByCategory(Yng_Category yng_Category) {
		Long categoryId = yng_Category.getCategoryId();
		List<Yng_ItemCategory> itemCategoryList = itemCategoryDao.findAll().stream() 			//convert list to stream
                .filter(itemCategory -> categoryId==itemCategory.getCategory().getCategoryId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return itemCategoryList;
	}

}
