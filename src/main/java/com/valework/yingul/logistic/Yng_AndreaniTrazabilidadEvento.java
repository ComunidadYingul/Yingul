package com.valework.yingul.logistic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Yng_AndreaniTrazabilidadEvento {
	//@JsonProperty(value = "IdMotivo")
	private String IdMotivo="";
	//@JsonProperty(value = "IdEstado")
	private String IdEstado="";
	//@JsonProperty(value = "Motivo")
	private Yng_AndreaniTrazabilidadEventosMotivo Motivo= new Yng_AndreaniTrazabilidadEventosMotivo();
	//@JsonProperty(value = "Fecha")
	private String Fecha="";
	//@JsonProperty(value = "Estado")
	private String Estado="";
	//@JsonProperty(value = "Sucursal")
	private String Sucursal="";
	public Yng_AndreaniTrazabilidadEvento() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getIdMotivo() {
		return IdMotivo;
	}
	public void setIdMotivo(String idMotivo) {
		IdMotivo = idMotivo;
	}
	public String getIdEstado() {
		return IdEstado;
	}
	public void setIdEstado(String idEstado) {
		IdEstado = idEstado;
	}
	public Yng_AndreaniTrazabilidadEventosMotivo getMotivo() {
		return Motivo;
	}
	public void setMotivo(Yng_AndreaniTrazabilidadEventosMotivo motivo) {
		Motivo = motivo;
	}
	public String getFecha() {
		return Fecha;
	}
	public void setFecha(String fecha) {
		Fecha = fecha;
	}
	public String getEstado() {
		return Estado;
	}
	public void setEstado(String estado) {
		Estado = estado;
	}
	public String getSucursal() {
		return Sucursal;
	}
	public void setSucursal(String sucursal) {
		Sucursal = sucursal;
	}
	@Override
	public String toString() {
		return "Yng_AndreaniTrazabilidadEvento [IdMotivo=" + IdMotivo + ", IdEstado=" + IdEstado + ", Motivo=" + Motivo
				+ ", Fecha=" + Fecha + ", Estado=" + Estado + ", Sucursal=" + Sucursal + "]";
	}
	
}
