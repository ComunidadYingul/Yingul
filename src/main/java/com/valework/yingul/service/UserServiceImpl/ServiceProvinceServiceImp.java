package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.valework.yingul.dao.ServiceProvinceDao;
import com.valework.yingul.model.Yng_ServiceProvince;
import com.valework.yingul.service.ServiceProvinceService;

public class ServiceProvinceServiceImp implements ServiceProvinceService{
	@Autowired
	private ServiceProvinceDao serviceProvinceDao;
	
	public String deleteServiceProvinces(Yng_ServiceProvince serviceProvinceIng){
		serviceProvinceDao.delete(serviceProvinceIng.getServiceProvinceId());
		return null;
	}
	public String deleteAllServiceProvinces(List<Yng_ServiceProvince> serviceProvinceList) {
		
		return "";
	}
	
	@Override
	public void deleteServiceProvinces(Long id) {
		serviceProvinceDao.delete(id);
	}

}
