package com.valework.yingul.service;

import java.util.List;
import java.util.Set;
import com.valework.yingul.model.Yng_City;
import com.valework.yingul.model.Yng_Province;

public interface CityService {
	List<Yng_City> findByProvince(Yng_Province yng_province);
	Yng_City findByCityId(int cityId);
	List<Yng_City> findByProvince2(int cp);
	Set<Yng_City> findCitiesByName(String name);
}
