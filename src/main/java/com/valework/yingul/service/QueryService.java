package com.valework.yingul.service;

import java.util.List;
import com.valework.yingul.model.Yng_Query;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.Yng_Item;

public interface QueryService {
	List<Yng_Query> findByItem(Yng_Item yng_item);
	List<Yng_Query> findByUser(Yng_User yng_user);
}
