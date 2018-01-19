package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.SecurityDao;
import com.valework.yingul.model.Yng_Security;
import com.valework.yingul.service.SecurityMotorized;

@Service
public class SecurityMotorizedImpl implements SecurityMotorized{
	@Autowired
    private SecurityDao securityDao;

	public List<Yng_Security> findAll() {
		// TODO Auto-generated method stub
		return securityDao.findAll();
	}

	public Yng_Security findByName(String name) {
		// TODO Auto-generated method stub
		return securityDao.findByName(name);
	}

	public Yng_Security findBySecurityId(int securityId) {
		// TODO Auto-generated method stub
		return securityDao.findBySecurityId(securityId);
	}
	
	

}
