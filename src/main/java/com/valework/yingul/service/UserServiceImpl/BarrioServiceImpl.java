package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.BarrioDao;
import com.valework.yingul.model.Yng_Barrio;
import com.valework.yingul.model.Yng_City;
import com.valework.yingul.service.BarrioService;

@Service
public class BarrioServiceImpl implements BarrioService{

	@Autowired
	BarrioDao barrioDao;
	
	public List<Yng_Barrio> findByCity(Yng_City yng_City) {
		int cityId = yng_City.getCityId();
		List<Yng_Barrio> barrioList = barrioDao.findAll().stream() 			//convert list to stream
                .filter(barrio -> cityId==barrio.getYng_City().getCityId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return barrioList;
	}

}
