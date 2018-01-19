package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Cotizacion;


public interface CotizacionService {
	List<Yng_Cotizacion> findAll();
	Yng_Cotizacion findByCotizacionId(Long id);

}
