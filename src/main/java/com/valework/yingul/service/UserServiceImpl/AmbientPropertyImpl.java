package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.AmbientDao;
import com.valework.yingul.model.Yng_Ambient;
import com.valework.yingul.service.AmbientProperty;

@Service
public class AmbientPropertyImpl implements AmbientProperty {

	@Autowired
	private AmbientDao ambientDao;
	

	public List<Yng_Ambient> findAll() {
		// TODO Auto-generated method stub
		return ambientDao.findAll();
	}


	public Yng_Ambient findByAmbientId(int ambientId) {
		// TODO Auto-generated method stub
		return ambientDao.findByAmbientId(ambientId);
	}

	public Yng_Ambient findByName(String name) {
		// TODO Auto-generated method stub
		return ambientDao.findByName(name);
	}

}
