package com.valework.yingul.service;

import java.util.List;
import java.util.Set;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_ServiceProvince;

public interface ServiceService {
	
	Yng_Service findByServiceId(Long Id);
	Yng_Service createService(Yng_Service service, Set<Yng_ServiceProvince> cobertureZone);	
	List<Yng_Service> findByItem(Yng_Item yng_item);
	boolean findByItemIdExist(Long idItem);
}
