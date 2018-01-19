package com.valework.yingul.service;

import java.util.List;


import com.valework.yingul.model.Yng_Security;

public interface SecurityMotorized {
	List<Yng_Security> findAll();
	Yng_Security findByName(String name);
	Yng_Security findBySecurityId(int securityId);

}
