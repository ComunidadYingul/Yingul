package com.valework.yingul.logistic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Yng_AndreaniTrazabilidad {	
	//@JsonProperty(value = "FechaAlta")
	private String FechaAlta="";
	//@JsonProperty(value = "Eventos")
	Yng_AndreaniTrazabilidadEventos Eventos=new Yng_AndreaniTrazabilidadEventos();
	//@JsonProperty(value = "NombreEnvio")
	private String NombreEnvio="";
	//@JsonProperty(value = "NroAndreani")
	private String NroAndreani="";
	public String getFechaAlta() {
		return FechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		FechaAlta = fechaAlta;
	}
	public Yng_AndreaniTrazabilidadEventos getEventos() {
		return Eventos;
	}
	public void setEventos(Yng_AndreaniTrazabilidadEventos eventos) {
		Eventos = eventos;
	}
	public String getNombreEnvio() {
		return NombreEnvio;
	}
	public void setNombreEnvio(String nombreEnvio) {
		NombreEnvio = nombreEnvio;
	}
	public String getNroAndreani() {
		return NroAndreani;
	}
	public void setNroAndreani(String nroAndreani) {
		NroAndreani = nroAndreani;
	}
	public Yng_AndreaniTrazabilidad() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Yng_AndreaniTrazabilidad [FechaAlta=" + FechaAlta + ", Eventos=" + Eventos + ", NombreEnvio="
				+ NombreEnvio + ", NroAndreani=" + NroAndreani + "]";
	}
	
	
}
