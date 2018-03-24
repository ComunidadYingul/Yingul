package com.valework.yingul.model;

public class Yng_StateShipping {
	private String NombreEnvio="";
	private String NroAndreani="";
	private String FechaAlta="";
	private String Fecha="";
	private String Estado="";
	private String Motivo="";
	private String Sucursal="";
	public Yng_StateShipping() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getFechaAlta() {
		return FechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		FechaAlta = fechaAlta;
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
	public String getMotivo() {
		return Motivo;
	}
	public void setMotivo(String motivo) {
		Motivo = motivo;
	}
	public String getSucursal() {
		return Sucursal;
	}
	public void setSucursal(String sucursal) {
		Sucursal = sucursal;
	}
	@Override
	public String toString() {
		return "Yng_StateShipping [NombreEnvio=" + NombreEnvio + ", NroAndreani=" + NroAndreani + ", FechaAlta="
				+ FechaAlta + ", Fecha=" + Fecha + ", Estado=" + Estado + ", Motivo=" + Motivo + ", Sucursal="
				+ Sucursal + "]";
	}
	
}
