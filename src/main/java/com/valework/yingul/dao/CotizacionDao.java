package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.valework.yingul.model.Yng_Cotizacion;

public interface CotizacionDao extends CrudRepository<Yng_Cotizacion, Long>{
	Yng_Cotizacion findByCotizacionId(Long id);
	List<Yng_Cotizacion> findAll();

}
