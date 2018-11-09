package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Shipment;

public interface ShipmentDao extends CrudRepository<Yng_Shipment, Long>{
	List<Yng_Shipment> findAll();
}
