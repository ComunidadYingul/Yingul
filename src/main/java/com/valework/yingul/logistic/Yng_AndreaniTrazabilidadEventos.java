package com.valework.yingul.logistic;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Yng_AndreaniTrazabilidadEventos {
	//@JsonProperty(value = "Evento_")
	List<Yng_AndreaniTrazabilidadEvento> Evento_=new ArrayList<>();

	public Yng_AndreaniTrazabilidadEventos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Yng_AndreaniTrazabilidadEvento> getEvento_() {
		return Evento_;
	}

	public void setEvento_(List<Yng_AndreaniTrazabilidadEvento> evento_) {
		Evento_ = evento_;
	}

	@Override
	public String toString() {
		return "Yng_AndreaniTrazabilidadEventos [Evento_=" + Evento_ + "]";
	}
	
}
