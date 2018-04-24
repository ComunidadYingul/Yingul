package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_ServiceProvince;

public interface ServiceProvinceDao extends CrudRepository<Yng_ServiceProvince, Long>{
	Yng_ServiceProvince findByServiceProvinceId(Long i);
}
