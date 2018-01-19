package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.valework.yingul.dao.MotorizedDao;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.service.MotorizedService;

@Service
@Transactional
public class MotorizedServiceImpl implements MotorizedService{
	@Autowired
	private MotorizedDao motorizedDao;
	
	public List<Yng_Motorized> findByItem(Yng_Item yng_item) {
		Long itemId = yng_item.getItemId();
		List<Yng_Motorized> motorizedList = motorizedDao.findAll().stream() 			//convert list to stream
                .filter(motorized -> itemId==motorized.getYng_Item().getItemId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return motorizedList;
	}

}
