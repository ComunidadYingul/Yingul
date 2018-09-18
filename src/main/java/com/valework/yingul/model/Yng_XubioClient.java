package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Yng_XubioClient {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "xubioClientId", nullable = false, updatable = false)
    private Long xubioClientId;
	private String CUIT;
	private String categoriaFiscal;
	private String codeCategoriaFiscal;
	private Long cliente_id;
	private String codigoPostal;
	private String direccion;
	private String email;
	private String identificacionTributaria;
	private String codeIdentificacionTributaria;
	private String nombre;
	private String razonSocial;
	private String telefono;
	private String usrCode;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private Yng_User user;
	
	public Yng_XubioClient() {
		super();
	}

	public Long getXubioClientId() {
		return xubioClientId;
	}

	public void setXubioClientId(Long xubioClientId) {
		this.xubioClientId = xubioClientId;
	}

	public String getCUIT() {
		return CUIT;
	}

	public void setCUIT(String cUIT) {
		CUIT = cUIT;
	}

	public String getCategoriaFiscal() {
		return categoriaFiscal;
	}

	public void setCategoriaFiscal(String categoriaFiscal) {
		this.categoriaFiscal = categoriaFiscal;
	}

	public String getCodeCategoriaFiscal() {
		return codeCategoriaFiscal;
	}

	public void setCodeCategoriaFiscal(String codeCategoriaFiscal) {
		this.codeCategoriaFiscal = codeCategoriaFiscal;
	}

	public Long getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Long cliente_id) {
		this.cliente_id = cliente_id;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentificacionTributaria() {
		return identificacionTributaria;
	}

	public void setIdentificacionTributaria(String identificacionTributaria) {
		this.identificacionTributaria = identificacionTributaria;
	}

	public String getCodeIdentificacionTributaria() {
		return codeIdentificacionTributaria;
	}

	public void setCodeIdentificacionTributaria(String codeIdentificacionTributaria) {
		this.codeIdentificacionTributaria = codeIdentificacionTributaria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUsrCode() {
		return usrCode;
	}

	public void setUsrCode(String usrCode) {
		this.usrCode = usrCode;
	}

	public Yng_User getUser() {
		return user;
	}

	public void setUser(Yng_User user) {
		this.user = user;
	}

}
