package com.valework.yingul.service;

import java.util.List;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Motorized;

public interface MotorizedService {
	List<Yng_Motorized> findByItem(Yng_Item yng_item);
	boolean findByItemIdExist(Long idItem);
}
