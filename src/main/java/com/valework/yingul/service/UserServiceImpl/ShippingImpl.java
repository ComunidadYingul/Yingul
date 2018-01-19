package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.valework.yingul.dao.ShippingDao;
import com.valework.yingul.model.Yng_Shipping;
import com.valework.yingul.service.ShippingService;

public class ShippingImpl implements ShippingService {

	@Autowired
	private ShippingDao shippingDao;
	@Override
	public List<Yng_Shipping> findAll() {
		// TODO Auto-generated method stub
		return this.shippingDao.findAll();
	}

	@Override
	public Yng_Shipping findByShippuingId(Long id) {
		// TODO Auto-generated method stub
		return this.shippingDao.findByShippingId(id);
	}

}
