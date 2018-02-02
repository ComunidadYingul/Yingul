
package com.valework.yingul.service.UserServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.model.Yng_City;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.model.Yng_Province;
import com.valework.yingul.service.CityService;

@Service
public class CityServiceImpl implements CityService{

	@Autowired 
	private CityDao cityDao;
	
	public List<Yng_City> findByProvince(Yng_Province yng_province) {
		int provinceId = yng_province.getProvinceId();
		List<Yng_City> cityList = cityDao.findByOrderByNameAsc().stream() 			//convert list to stream
                .filter(city -> provinceId==city.getYng_Province().getProvinceId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return cityList;
	}

	public Yng_City findByCityId(int cityId) {
		return cityDao.findByCityId(cityId);
	}
	public List<Yng_City> findByProvince2(int cp) {
		//int provinceId = yng_province.getProvinceId();
		String postalCode=""+cp;
		List<Yng_City> cityList = cityDao.findAll().stream() 			//convert list to stream
                .filter(city -> postalCode.equals(city.getCodigopostal()))	//filters the line, equals to username
                .collect(Collectors.toList());
        return cityList;
	}
	@Override
	public Set<Yng_City> findCitiesByName(String name) {
		List<Yng_City> cityListTemp = cityDao.findAll();
		Set<Yng_City> cityList = new HashSet<>();
		for (Yng_City s : cityListTemp) {
			if(s.getName()!=null) {
				if(s.getName().replace(" ","").toUpperCase().contains(name.replace(" ","").toUpperCase())) {
					if(cityList.size()<=10){
					cityList.add(s);}
					else {return cityList;}
				}
			}
    	}
		return cityList;
	}
	

}
