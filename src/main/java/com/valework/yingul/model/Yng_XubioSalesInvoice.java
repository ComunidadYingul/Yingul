package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Yng_XubioSalesInvoice {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "xubioSalesInvoiceId", nullable = false, updatable = false)
    private Long xubioSalesInvoiceId;
	private String externalId;
	private String circuitoContable;
	private String codeCircuitoContable;
	private Long transaccionid;
	private int tipo;
	private String fecha;
	private String fechaVto;
	private String puntoVenta;
	private String codePuntoVenta;
	private String numeroDocumento;
	private int condicionDePago;
	private String deposito;
	private String codeDeposito;
	private String moneda;
	private String codeMoneda;
	private int cotizacion;
	private double importetotal;
	private double importeImpuestos;
	private double importeGravado;
	private int cotizacionListaDePrecio;
	private int porcentajeComision;
	private String descripcion;
	private boolean CBUInformada;
	private boolean facturaNoExportacion;
	private String type;
	private String CAE;
	private String CAEFechaVto;
	
	private String yngDescription;
	private String yngStatus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xubio_client_id")
	private Yng_XubioClient xubioClient;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "yng_confirm_id")
	private Yng_Confirm confirm;
	
	public Yng_XubioSalesInvoice() {
		super();
	}

	public Long getXubioSalesInvoiceId() {
		return xubioSalesInvoiceId;
	}

	public void setXubioSalesInvoiceId(Long xubioSalesInvoiceId) {
		this.xubioSalesInvoiceId = xubioSalesInvoiceId;
	}

	public String getCircuitoContable() {
		return circuitoContable;
	}

	public void setCircuitoContable(String circuitoContable) {
		this.circuitoContable = circuitoContable;
	}

	public String getCodeCircuitoContable() {
		return codeCircuitoContable;
	}

	public void setCodeCircuitoContable(String codeCircuitoContable) {
		this.codeCircuitoContable = codeCircuitoContable;
	}

	public Long getTransaccionid() {
		return transaccionid;
	}

	public void setTransaccionid(Long transaccionid) {
		this.transaccionid = transaccionid;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFechaVto() {
		return fechaVto;
	}

	public void setFechaVto(String fechaVto) {
		this.fechaVto = fechaVto;
	}

	public String getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(String puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public String getCodePuntoVenta() {
		return codePuntoVenta;
	}

	public void setCodePuntoVenta(String codePuntoVenta) {
		this.codePuntoVenta = codePuntoVenta;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public int getCondicionDePago() {
		return condicionDePago;
	}

	public void setCondicionDePago(int condicionDePago) {
		this.condicionDePago = condicionDePago;
	}

	public String getDeposito() {
		return deposito;
	}

	public void setDeposito(String deposito) {
		this.deposito = deposito;
	}

	public String getCodeDeposito() {
		return codeDeposito;
	}

	public void setCodeDeposito(String codeDeposito) {
		this.codeDeposito = codeDeposito;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getCodeMoneda() {
		return codeMoneda;
	}

	public void setCodeMoneda(String codeMoneda) {
		this.codeMoneda = codeMoneda;
	}

	public int getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(int cotizacion) {
		this.cotizacion = cotizacion;
	}

	public double getImportetotal() {
		return importetotal;
	}

	public void setImportetotal(double importetotal) {
		this.importetotal = importetotal;
	}

	public double getImporteImpuestos() {
		return importeImpuestos;
	}

	public void setImporteImpuestos(double importeImpuestos) {
		this.importeImpuestos = importeImpuestos;
	}

	public double getImporteGravado() {
		return importeGravado;
	}

	public void setImporteGravado(double importeGravado) {
		this.importeGravado = importeGravado;
	}

	public int getCotizacionListaDePrecio() {
		return cotizacionListaDePrecio;
	}

	public void setCotizacionListaDePrecio(int cotizacionListaDePrecio) {
		this.cotizacionListaDePrecio = cotizacionListaDePrecio;
	}

	public int getPorcentajeComision() {
		return porcentajeComision;
	}

	public void setPorcentajeComision(int porcentajeComision) {
		this.porcentajeComision = porcentajeComision;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isCBUInformada() {
		return CBUInformada;
	}

	public void setCBUInformada(boolean cBUInformada) {
		CBUInformada = cBUInformada;
	}

	public boolean isFacturaNoExportacion() {
		return facturaNoExportacion;
	}

	public void setFacturaNoExportacion(boolean facturaNoExportacion) {
		this.facturaNoExportacion = facturaNoExportacion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCAE() {
		return CAE;
	}

	public void setCAE(String cAE) {
		CAE = cAE;
	}

	public String getCAEFechaVto() {
		return CAEFechaVto;
	}

	public void setCAEFechaVto(String cAEFechaVto) {
		CAEFechaVto = cAEFechaVto;
	}

	public String getYngDescription() {
		return yngDescription;
	}

	public void setYngDescription(String yngDescription) {
		this.yngDescription = yngDescription;
	}

	public String getYngStatus() {
		return yngStatus;
	}

	public void setYngStatus(String yngStatus) {
		this.yngStatus = yngStatus;
	}

	public Yng_XubioClient getXubioClient() {
		return xubioClient;
	}

	public void setXubioClient(Yng_XubioClient xubioClient) {
		this.xubioClient = xubioClient;
	}

	public Yng_Confirm getConfirm() {
		return confirm;
	}

	public void setConfirm(Yng_Confirm confirm) {
		this.confirm = confirm;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
}
