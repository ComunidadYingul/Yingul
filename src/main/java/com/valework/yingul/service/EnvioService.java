package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Envio;

public interface EnvioService {
	List<Yng_Envio> findAll();
	Yng_Envio findByEnvioId(Long id);


}
