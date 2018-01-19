package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_Cotizacion {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cotizacionId", nullable = false, updatable = false)
    private Long cotizacionId;


	private String called;
	private String respond;
	private String fecha;
	private String idUser;
	private String itemId;
	//****************************
    private String CategoriaDistancia;
    private String CategoriaDistanciaId;
    private String CategoriaPeso;
    private String CategoriaPesoId;
    private String PesoAforado;
    private String Tarifa;
    //**********************
    private String Descripcion;
    private String Direccion;
    private String HoradeTrabajo;
    private String Latitud;
    private String Longitud;
    private String Mail,Numero,Responsable,Resumen;
    private String Sucursal;
    private String Telefono1;
    private String Telefono2;
    private String Telefono3;
    private String TipoSucursal;
    private String TipoTelefono1;
    private String TipoTelefono2;
    private String TipoTelefono3;
	//private String ip;
	//private String branchId;
	@Override
	public String toString() {
		return "Yng_Cotizacion [cotizacionId=" + cotizacionId + ", called=" + called + ", respond=" + respond
				+ ", fecha=" + fecha + ", idUser=" + idUser + ", itemId=" + itemId + ", CategoriaDistancia="
				+ CategoriaDistancia + ", CategoriaDistanciaId=" + CategoriaDistanciaId + ", CategoriaPeso="
				+ CategoriaPeso + ", CategoriaPesoId=" + CategoriaPesoId + ", PesoAforado=" + PesoAforado + ", Tarifa="
				+ Tarifa + ", Descripcion=" + Descripcion + ", Direccion=" + Direccion + ", HoradeTrabajo="
				+ HoradeTrabajo + ", Latitud=" + Latitud + ", Longitud=" + Longitud + ", Mail=" + Mail + ", Numero="
				+ Numero + ", Responsable=" + Responsable + ", Resumen=" + Resumen + ", Sucursal=" + Sucursal
				+ ", Telefono1=" + Telefono1 + ", Telefono2=" + Telefono2 + ", Telefono3=" + Telefono3
				+ ", TipoSucursal=" + TipoSucursal + ", TipoTelefono1=" + TipoTelefono1 + ", TipoTelefono2="
				+ TipoTelefono2 + ", TipoTelefono3=" + TipoTelefono3 + "]";
	}
	public Long getCotizacionId() {
		return cotizacionId;
	}
	public void setCotizacionId(Long cotizacionId) {
		this.cotizacionId = cotizacionId;
	}
	public String getCalled() {
		return called;
	}
	public void setCalled(String called) {
		this.called = called;
	}
	public String getRespond() {
		return respond;
	}
	public void setRespond(String respond) {
		this.respond = respond;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getCategoriaDistancia() {
		return CategoriaDistancia;
	}
	public void setCategoriaDistancia(String categoriaDistancia) {
		CategoriaDistancia = categoriaDistancia;
	}
	public String getCategoriaDistanciaId() {
		return CategoriaDistanciaId;
	}
	public void setCategoriaDistanciaId(String categoriaDistanciaId) {
		CategoriaDistanciaId = categoriaDistanciaId;
	}
	public String getCategoriaPeso() {
		return CategoriaPeso;
	}
	public void setCategoriaPeso(String categoriaPeso) {
		CategoriaPeso = categoriaPeso;
	}
	public String getCategoriaPesoId() {
		return CategoriaPesoId;
	}
	public void setCategoriaPesoId(String categoriaPesoId) {
		CategoriaPesoId = categoriaPesoId;
	}
	public String getPesoAforado() {
		return PesoAforado;
	}
	public void setPesoAforado(String pesoAforado) {
		PesoAforado = pesoAforado;
	}
	public String getTarifa() {
		return Tarifa;
	}
	public void setTarifa(String tarifa) {
		Tarifa = tarifa;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	public String getHoradeTrabajo() {
		return HoradeTrabajo;
	}
	public void setHoradeTrabajo(String horadeTrabajo) {
		HoradeTrabajo = horadeTrabajo;
	}
	public String getLatitud() {
		return Latitud;
	}
	public void setLatitud(String latitud) {
		Latitud = latitud;
	}
	public String getLongitud() {
		return Longitud;
	}
	public void setLongitud(String longitud) {
		Longitud = longitud;
	}
	public String getMail() {
		return Mail;
	}
	public void setMail(String mail) {
		Mail = mail;
	}
	public String getNumero() {
		return Numero;
	}
	public void setNumero(String numero) {
		Numero = numero;
	}
	public String getResponsable() {
		return Responsable;
	}
	public void setResponsable(String responsable) {
		Responsable = responsable;
	}
	public String getResumen() {
		return Resumen;
	}
	public void setResumen(String resumen) {
		Resumen = resumen;
	}
	public String getSucursal() {
		return Sucursal;
	}
	public void setSucursal(String sucursal) {
		Sucursal = sucursal;
	}
	public String getTelefono1() {
		return Telefono1;
	}
	public void setTelefono1(String telefono1) {
		Telefono1 = telefono1;
	}
	public String getTelefono2() {
		return Telefono2;
	}
	public void setTelefono2(String telefono2) {
		Telefono2 = telefono2;
	}
	public String getTelefono3() {
		return Telefono3;
	}
	public void setTelefono3(String telefono3) {
		Telefono3 = telefono3;
	}
	public String getTipoSucursal() {
		return TipoSucursal;
	}
	public void setTipoSucursal(String tipoSucursal) {
		TipoSucursal = tipoSucursal;
	}
	public String getTipoTelefono1() {
		return TipoTelefono1;
	}
	public void setTipoTelefono1(String tipoTelefono1) {
		TipoTelefono1 = tipoTelefono1;
	}
	public String getTipoTelefono2() {
		return TipoTelefono2;
	}
	public void setTipoTelefono2(String tipoTelefono2) {
		TipoTelefono2 = tipoTelefono2;
	}
	public String getTipoTelefono3() {
		return TipoTelefono3;
	}
	public void setTipoTelefono3(String tipoTelefono3) {
		TipoTelefono3 = tipoTelefono3;
	}
    
    
    
    
	
	
		
}
