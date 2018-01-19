
package com.valework.yingul.model;

public class Yng_Cotizar {
	 	private int cotizarId;
		
		private String provincia;
		private String localidad;
		private String codigo_postal;
		private String peso;
		private String volumen;
		private String bultos;
		private String paquetes;
		private String correo;
		private String modalidad;
		private String servicio;
		private String direccion_envio;
		private String orden_columna;
		private String orden_sentido;
		private String itemID;
		private String codAndreani;
	    private String precio;
	
		public final static String ordenColumnaAsce ="asc";
		public final static String ordenColumnaDesc="desc";
		public final String modalidadEnvioDomicilio="D";
		public final String modalidadEnvioSucursal="S";
		public final String servicioEstandar="N";
		public final String servicioPrioritario="";
		public final String servicioExpres="X";
		public final String servicioDevoluciones="R";
		public final String ordenColumnaValor="valor";
		public final String ordenColumnaHorasEntrega="horas_entrega";
		public final String ordenColumnaCumplimiento="cumplimiento";
		public final String ordenColumnaAnomalos="anomalos";
		
		
		public int getCotizarId() {
			return cotizarId;
		}
		public void setCotizarId(int cotizarId) {
			this.cotizarId = cotizarId;
		}
		public String getProvincia() {
			return provincia;
		}
		public void setProvincia(String provincia) {
			this.provincia = provincia;
		}
		public String getLocalidad() {
			return localidad;
		}
		public void setLocalidad(String localidad) {
			this.localidad = localidad;
		}
		
		
	
		public String getCodigo_postal() {
			return codigo_postal;
		}
		public void setCodigo_postal(String codigo_postal) {
			this.codigo_postal = codigo_postal;
		}

		public String getPeso() {
			return peso;
		}
		public void setPeso(String peso) {
			this.peso = peso;
		}
		public String getVolumen() {
			return volumen;
		}
		public void setVolumen(String volumen) {
			this.volumen = volumen;
		}
		public String getBultos() {
			return bultos;
		}
		public void setBultos(String bultos) {
			this.bultos = bultos;
		}
		public String getPaquetes() {
			return paquetes;
		}
		public void setPaquetes(String paquetes) {
			this.paquetes = paquetes;
		}
		public String getCorreo() {
			return correo;
		}
		public void setCorreo(String correo) {
			this.correo = correo;
		}
		public String getModalidad() {
			return modalidad;
		}
		public void setModalidad(String modalidad) {
			this.modalidad = modalidad;
		}
		public String getServicio() {
			return servicio;
		}
		public void setServicio(String servicio) {
			this.servicio = servicio;
		}
		public String getDireccion_envio() {
			return direccion_envio;
		}
		public void setDireccion_envio(String direccion_envio) {
			this.direccion_envio = direccion_envio;
		}
		public String getOrden_columna() {
			return orden_columna;
		}
		public void setOrden_columna(String orden_columna) {
			this.orden_columna = orden_columna;
		}
		public String getOrden_sentido() {
			return orden_sentido;
		}
		public void setOrden_sentido(String orden_sentido) {
			this.orden_sentido = orden_sentido;
		}
		public String getItemID() {
			return itemID;
		}
		public void setItemID(String itemID) {
			this.itemID = itemID;
		}
		public String getCodAndreani() {
			return codAndreani;
		}
		public void setCodAndreani(String codAndreani) {
			this.codAndreani = codAndreani;
		}
		public String getPrecio() {
			return precio;
		}
		public void setPrecio(String precio) {
			this.precio = precio;
		}
		@Override
		public String toString() {
			return "Yng_Cotizar [cotizarId=" + cotizarId + ", provincia=" + provincia + ", localidad=" + localidad
					+ ", codigo_postal=" + codigo_postal + ", peso=" + peso + ", volumen=" + volumen + ", bultos="
					+ bultos + ", paquetes=" + paquetes + ", correo=" + correo + ", modalidad=" + modalidad
					+ ", servicio=" + servicio + ", direccion_envio=" + direccion_envio + ", orden_columna="
					+ orden_columna + ", orden_sentido=" + orden_sentido + ", itemID=" + itemID + ", codAndreani="
					+ codAndreani + ", precio=" + precio + ", ordenColumnaAsce=" + ordenColumnaAsce
					+ ", ordenColumnaDesc=" + ordenColumnaDesc + ", modalidadEnvioDomicilio=" + modalidadEnvioDomicilio
					+ ", modalidadEnvioSucursal=" + modalidadEnvioSucursal + ", servicioEstandar=" + servicioEstandar
					+ ", servicioPrioritario=" + servicioPrioritario + ", servicioExpres=" + servicioExpres
					+ ", servicioDevoluciones=" + servicioDevoluciones + ", ordenColumnaValor=" + ordenColumnaValor
					+ ", ordenColumnaHorasEntrega=" + ordenColumnaHorasEntrega + ", ordenColumnaCumplimiento="
					+ ordenColumnaCumplimiento + ", ordenColumnaAnomalos=" + ordenColumnaAnomalos + "]";
		}
		
		
		
		


	 
		

		
		
		
		
		
		
		
		

}
