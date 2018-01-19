package com.valework.yingul.model;

public class Yng_AndreaniSucursal {
	private int sucursalId;
	
	private String username;
	private String password;
	private String localidad;
	private String codigoPostal;
	private String provincia;
	@Override
	public String toString() {
		return "Yng_AndreaniSucursal [sucursalId=" + sucursalId + ", username=" + username + ", password=" + password
				+ ", localidad=" + localidad + ", codigoPostal=" + codigoPostal + ", provincia=" + provincia + "]";
	}
	public int getSucursalId() {
		return sucursalId;
	}
	public void setSucursalId(int sucursalId) {
		this.sucursalId = sucursalId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	


}
