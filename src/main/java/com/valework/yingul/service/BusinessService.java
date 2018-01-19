package com.valework.yingul.service;

import java.util.List;
import com.valework.yingul.model.Yng_Business;
import com.valework.yingul.model.Yng_User;


public interface BusinessService {
	Yng_Business createBusiness(Yng_Business business, Yng_User user);
	List <Yng_Business> findByUser(Yng_User yng_user);
}
