package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valework.yingul.dao.ItemImageDao;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemImage;
import com.valework.yingul.service.ItemImageService;

@Service
@Transactional
public class ItemImageServiceImpl implements ItemImageService{

	@Autowired
	ItemImageDao itemImageDao;

	
	public List<Yng_ItemImage> findByItem(Yng_Item yng_Item) {
		Long itemId = yng_Item.getItemId();
		List<Yng_ItemImage> itemImageList = itemImageDao.findAll().stream() 			//convert list to stream
                .filter(itemImage -> itemId==itemImage.getItem().getItemId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return itemImageList;
	}

}
