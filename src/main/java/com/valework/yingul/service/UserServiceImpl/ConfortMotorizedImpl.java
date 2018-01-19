package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.valework.yingul.service.SecurityMotorized;

import com.valework.yingul.dao.ConfortDao;
import com.valework.yingul.model.Yng_Confort;
import com.valework.yingul.service.ConfortMotorized;

@Service
public class ConfortMotorizedImpl implements ConfortMotorized{

	@Autowired
	ConfortDao confortDao;


	public List<Yng_Confort> findAll() {
		// TODO Auto-generated method stub
		return confortDao.findAll();
	}


	public Yng_Confort findByName(String name) {
		// TODO Auto-generated method stub
		return confortDao.findByName(name);
	}


	public Yng_Confort findByConfortId(int confortId) {
		// TODO Auto-generated method stub
		return confortDao.findByConfortId(confortId);
	}

	

}
