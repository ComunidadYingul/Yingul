package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.ExteriorDao;
import com.valework.yingul.model.Yng_Exterior;
import com.valework.yingul.service.ExteriorMotorized;

@Service
public class ExteriorMotorizedImpl implements ExteriorMotorized {

	@Autowired
	private ExteriorDao exteriorDao;
	public List<Yng_Exterior> findAll() {
		// TODO Auto-generated method stub
		return this.exteriorDao.findAll();
	}


	public Yng_Exterior findByExteriorId(int exteriorId) {
		// TODO Auto-generated method stub
		return this.exteriorDao.findByExteriorId(exteriorId);
	}


	public Yng_Exterior findByName(String name) {
		// TODO Auto-generated method stub
		return this.findByName(name);
	}
	

}
