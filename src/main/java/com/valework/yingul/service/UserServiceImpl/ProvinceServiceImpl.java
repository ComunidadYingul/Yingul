package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.model.Yng_Country;
import com.valework.yingul.model.Yng_Province;
import com.valework.yingul.model.Yng_ResponseHeader;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.service.ProvinceService;

@Service
public class ProvinceServiceImpl implements ProvinceService{

	@Autowired
    private ProvinceDao provinceDao;
	private List<Yng_Province> ret;
	
	public List<Yng_Province> findAll() {
		return provinceDao.findByOrderByNameAsc();
	}

	public Yng_Province findByName(String name) {
		// TODO Auto-generated method stub
		return provinceDao.findByName(name);
	}

	public Yng_Province findByProvinceId(int provinceId) {
		// TODO Auto-generated method stub
		return provinceDao.findByProvinceId(provinceId);
	}

	public List<Yng_Province> findByCounttry(int country) {
		List<Yng_Province> provinceList = provinceDao.findByOrderByNameAsc().stream() 			//convert list to stream
                .filter(province -> country==province.getYng_Country().getCountryId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return provinceList;
	}
	
}
