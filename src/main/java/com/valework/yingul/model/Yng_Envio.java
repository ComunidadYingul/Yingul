package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_Envio {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "envioId", nullable = false, updatable = false)
    private Long envioId;
	//private String tarifa;
	private String numeroAndreani="";
	private String pdfLink="";
	private String calle;
	private String categoriaDistancia="";
	private String categoriaFacturacion="";
	private String categoriaPeso="";
	private String codigoPostalDestino="";
	private String contrato="";
	private String departamento="";
	private String detalleProductosEntrega="";
	private String detalleProductosRetiro="";
	private String email="";
	private String localidad="";
	private String nombreApellido="";
	private String nombreApellidoAlternativo="";
	private String numero="";
	private String numeroCelular="";
	private String numeroDocumento="";
	private String numeroTelefono="";
	private String numeroTransaccion="";
	private String peso="";
	private String piso="";
	private String provincia="";
	private String sucursalCliente="";
	private String sucursalRetiro="";
	private String tarifa="";
	private String tipoDocumento="";
	private String valorACobrar="";
	private String valorDeclarado="";
	private String volumen="";
	
	
	
	public String getNumeroAndreani() {
		return numeroAndreani;
	}
	public void setNumeroAndreani(String numeroAndreani) {
		this.numeroAndreani = numeroAndreani;
	}
	public String getPdfLink() {
		return pdfLink;
	}
	public void setPdfLink(String pdfLink) {
		this.pdfLink = pdfLink;
	}

	public Long getEnvioId() {
		return envioId;
	}
	public void setEnvioId(Long envioId) {
		this.envioId = envioId;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getCategoriaDistancia() {
		return categoriaDistancia;
	}
	public void setCategoriaDistancia(String categoriaDistancia) {
		this.categoriaDistancia = categoriaDistancia;
	}
	public String getCategoriaFacturacion() {
		return categoriaFacturacion;
	}
	public void setCategoriaFacturacion(String categoriaFacturacion) {
		this.categoriaFacturacion = categoriaFacturacion;
	}
	public String getCategoriaPeso() {
		return categoriaPeso;
	}
	public void setCategoriaPeso(String categoriaPeso) {
		this.categoriaPeso = categoriaPeso;
	}
	public String getCodigoPostalDestino() {
		return codigoPostalDestino;
	}
	public void setCodigoPostalDestino(String codigoPostalDestino) {
		this.codigoPostalDestino = codigoPostalDestino;
	}
	public String getContrato() {
		return contrato;
	}
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getDetalleProductosEntrega() {
		return detalleProductosEntrega;
	}
	public void setDetalleProductosEntrega(String detalleProductosEntrega) {
		this.detalleProductosEntrega = detalleProductosEntrega;
	}
	public String getDetalleProductosRetiro() {
		return detalleProductosRetiro;
	}
	public void setDetalleProductosRetiro(String detalleProductosRetiro) {
		this.detalleProductosRetiro = detalleProductosRetiro;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getNombreApellido() {
		return nombreApellido;
	}
	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}
	public String getNombreApellidoAlternativo() {
		return nombreApellidoAlternativo;
	}
	public void setNombreApellidoAlternativo(String nombreApellidoAlternativo) {
		this.nombreApellidoAlternativo = nombreApellidoAlternativo;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNumeroCelular() {
		return numeroCelular;
	}
	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getNumeroTelefono() {
		return numeroTelefono;
	}
	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}
	public String getNumeroTransaccion() {
		return numeroTransaccion;
	}
	public void setNumeroTransaccion(String numeroTransaccion) {
		this.numeroTransaccion = numeroTransaccion;
	}
	public String getPeso() {
		return peso;
	}
	public void setPeso(String peso) {
		this.peso = peso;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getSucursalCliente() {
		return sucursalCliente;
	}
	public void setSucursalCliente(String sucursalCliente) {
		this.sucursalCliente = sucursalCliente;
	}
	public String getSucursalRetiro() {
		return sucursalRetiro;
	}
	public void setSucursalRetiro(String sucursalRetiro) {
		this.sucursalRetiro = sucursalRetiro;
	}
	public String getTarifa() {
		return tarifa;
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getValorACobrar() {
		return valorACobrar;
	}
	public void setValorACobrar(String valorACobrar) {
		this.valorACobrar = valorACobrar;
	}
	public String getValorDeclarado() {
		return valorDeclarado;
	}
	public void setValorDeclarado(String valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}
	public String getVolumen() {
		return volumen;
	}
	public void setVolumen(String volumen) {
		this.volumen = volumen;
	}
	@Override
	public String toString() {
		return "Yng_Envio [envioId=" + envioId + ", numeroAndreani=" + numeroAndreani + ", pdfLink=" + pdfLink
				+ ", calle=" + calle + ", categoriaDistancia=" + categoriaDistancia + ", categoriaFacturacion="
				+ categoriaFacturacion + ", categoriaPeso=" + categoriaPeso + ", codigoPostalDestino="
				+ codigoPostalDestino + ", contrato=" + contrato + ", departamento=" + departamento
				+ ", detalleProductosEntrega=" + detalleProductosEntrega + ", detalleProductosRetiro="
				+ detalleProductosRetiro + ", email=" + email + ", localidad=" + localidad + ", nombreApellido="
				+ nombreApellido + ", nombreApellidoAlternativo=" + nombreApellidoAlternativo + ", numero=" + numero
				+ ", numeroCelular=" + numeroCelular + ", numeroDocumento=" + numeroDocumento + ", numeroTelefono="
				+ numeroTelefono + ", numeroTransaccion=" + numeroTransaccion + ", peso=" + peso + ", piso=" + piso
				+ ", provincia=" + provincia + ", sucursalCliente=" + sucursalCliente + ", sucursalRetiro="
				+ sucursalRetiro + ", tarifa=" + tarifa + ", tipoDocumento=" + tipoDocumento + ", valorACobrar="
				+ valorACobrar + ", valorDeclarado=" + valorDeclarado + ", volumen=" + volumen + "]";
	}
	
}
