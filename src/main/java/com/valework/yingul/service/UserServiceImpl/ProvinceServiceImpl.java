package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.model.Yng_Province;
import com.valework.yingul.service.ProvinceService;

@Service
public class ProvinceServiceImpl implements ProvinceService{

	@Autowired
    private ProvinceDao provinceDao;
	
	public List<Yng_Province> findAll() {
		return provinceDao.findAll();
	}

	public Yng_Province findByName(String name) {
		// TODO Auto-generated method stub
		return provinceDao.findByName(name);
	}

	public Yng_Province findByProvinceId(int provinceId) {
		// TODO Auto-generated method stub
		return provinceDao.findByProvinceId(provinceId);
	}
	
}
