package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Cotizacion;
import com.valework.yingul.model.Yng_Envio;

public interface EnvioDao extends CrudRepository<Yng_Envio, Long>{

	List<Yng_Envio> findAll();
	Yng_Cotizacion findByEnvioId(Long id);
}
