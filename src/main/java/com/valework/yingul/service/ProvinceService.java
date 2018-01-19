package com.valework.yingul.service;

import java.util.List;
import com.valework.yingul.model.Yng_Province;

public interface ProvinceService {
	List<Yng_Province> findAll();
	Yng_Province findByName(String name);
	Yng_Province findByProvinceId(int provinceId);
}
