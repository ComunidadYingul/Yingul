package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.valework.yingul.dao.PropertyDao;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.service.PropertyService;

@Service
@Transactional
public class PropertyServiceImpl implements PropertyService{
	@Autowired
	private PropertyDao propertyDao;

	public List<Yng_Property> findByItem(Yng_Item yng_item) {
		Long itemId = yng_item.getItemId();
		List<Yng_Property> propertyList = propertyDao.findAll().stream() 			//convert list to stream
                .filter(property -> itemId==property.getYng_Item().getItemId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return propertyList;
	}
}
