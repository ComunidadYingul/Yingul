package com.valework.yingul.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.valework.yingul.dao.ConfirmDao;
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.service.ConfirmService;

@Service
@Transactional
public class ConfirmServiceImpl implements ConfirmService{

	@Autowired
	ConfirmDao confirmDao;
	@Override
	public boolean exitsByConfirmId(Long confirmId) {
		for (Yng_Confirm s : confirmDao.findByOrderByConfirmIdDesc()) {
			if(s.getConfirmId().equals(confirmId)) {
				return true;
			}
    	}
		return false;
	}
	
}
