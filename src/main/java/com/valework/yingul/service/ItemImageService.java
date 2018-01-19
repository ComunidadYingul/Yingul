package com.valework.yingul.service;

import java.util.List;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemImage;

public interface ItemImageService {
	List<Yng_ItemImage> findByItem(Yng_Item yng_Item);

}
