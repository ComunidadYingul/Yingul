package com.valework.yingul.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.service.StandardService;

@Service
public class StandardServiceImpl implements StandardService{
	@Autowired
	private StandardDao standardDao;

	@Override
	public Yng_Standard findByItemId(Long id) {		
		return standardDao.findByStandardId(id);
	}

}
