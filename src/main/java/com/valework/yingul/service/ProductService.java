package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Product;

public interface ProductService {
	List<Yng_Product> findByItem(Yng_Item yng_item);

}
