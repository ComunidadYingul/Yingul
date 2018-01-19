package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.AmenitiesDao;
import com.valework.yingul.model.Yng_Amenities;
import com.valework.yingul.service.AmenitiesProperty;

@Service
public class AmenitiesPropertyImpl implements AmenitiesProperty{

	@Autowired
	private AmenitiesDao amenitiesDao;
	
	public List<Yng_Amenities> findAll() {
		// TODO Auto-generated method stub
		return this.amenitiesDao.findAll();
	}

	
	public Yng_Amenities findByName(String name) {
		// TODO Auto-generated method stub
		return this.amenitiesDao.findByName(name);
	}


	@Override
	public Yng_Amenities findByAmenitiesId(int amenitiesId) {
		// TODO Auto-generated method stub
		return this.amenitiesDao.findByAmenitiesId(amenitiesId);
	}

	


}
