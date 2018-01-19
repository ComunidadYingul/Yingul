package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valework.yingul.dao.CotizacionDao;
import com.valework.yingul.model.Yng_Cotizacion;
import com.valework.yingul.service.CotizacionService;

@Service
@Transactional
public class CotizacionImpl implements CotizacionService{
	@Autowired
	private CotizacionDao cotizacionDao;

	@Override
	public List<Yng_Cotizacion> findAll() {
		// TODO Auto-generated method stub
		return cotizacionDao.findAll();
	}

	@Override
	public Yng_Cotizacion findByCotizacionId(Long id) {
		// TODO Auto-generated method stub
		return cotizacionDao.findByCotizacionId(id);
	}


}
