package com.valework.yingul.service;

import java.util.List;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Property;

public interface PropertyService {
	List<Yng_Property> findByItem(Yng_Item yng_item);
}
