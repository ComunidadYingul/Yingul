package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.EquipmentDao;
import com.valework.yingul.model.Yng_Equipment;
import com.valework.yingul.service.EquipmentMotorized;

@Service
public class EquipmentMotorizedImpl implements EquipmentMotorized{
	@Autowired
	private EquipmentDao equipmentDao;

	public List<Yng_Equipment> findAll() {
		// TODO Auto-generated method stub
		return equipmentDao.findAll();
	}


	public Yng_Equipment findByName(String name) {
		// TODO Auto-generated method stub
		return equipmentDao.findByName(name);
	}


	public Yng_Equipment findByEquipmentId(int equipmentId) {
		// TODO Auto-generated method stub
		return equipmentDao.findByEquipmentId(equipmentId);
	}

}
