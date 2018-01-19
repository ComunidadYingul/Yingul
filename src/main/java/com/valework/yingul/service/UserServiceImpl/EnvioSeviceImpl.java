package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.valework.yingul.dao.EnvioDao;
import com.valework.yingul.model.Yng_Envio;
import com.valework.yingul.service.EnvioService;

@Service
@Transactional
public class EnvioSeviceImpl implements EnvioService{
	
	@Autowired
	private EnvioDao envioDao;
 
	@Override
	public List<Yng_Envio> findAll() {
		// TODO Auto-generated method stub
		return this.envioDao.findAll();
	}

	@Override
	public Yng_Envio findByEnvioId(Long id) {
		// TODO Auto-generated method stub
		return this.findByEnvioId(id);
	}




}
