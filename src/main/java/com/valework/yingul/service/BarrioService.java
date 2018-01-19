package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Barrio;
import com.valework.yingul.model.Yng_City;

public interface BarrioService {
	List<Yng_Barrio> findByCity(Yng_City yng_City);
}
