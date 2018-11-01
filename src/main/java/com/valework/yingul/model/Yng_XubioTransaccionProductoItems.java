package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Yng_XubioTransaccionProductoItems {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "xubioTransaccionProductoItemsId", nullable = false, updatable = false)
    private Long XubioTransaccionProductoItemsId;
	
	private double precioconivaincluido;
	private Long transaccionId;
	private String producto;
	private String codeProducto;
	private String deposito;
	private String codeDeposito;
	private String descripcion;
	private int cantidad;
	private double precio;
	private double iva;
	private double importe;
	private double total;
	private int procentajeDescuento;
	private double montoExtento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xubio_sales_invoice_id")
	private Yng_XubioSalesInvoice xubioSalesInvoice;
	
	public Yng_XubioTransaccionProductoItems() {
		super();
	}

	public Long getXubioTransaccionProductoItemsId() {
		return XubioTransaccionProductoItemsId;
	}

	public void setXubioTransaccionProductoItemsId(Long xubioTransaccionProductoItemsId) {
		XubioTransaccionProductoItemsId = xubioTransaccionProductoItemsId;
	}

	public double getPrecioconivaincluido() {
		return precioconivaincluido;
	}

	public void setPrecioconivaincluido(double precioconivaincluido) {
		this.precioconivaincluido = precioconivaincluido;
	}

	public Long getTransaccionId() {
		return transaccionId;
	}

	public void setTransaccionId(Long transaccionId) {
		this.transaccionId = transaccionId;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getCodeProducto() {
		return codeProducto;
	}

	public void setCodeProducto(String codeProducto) {
		this.codeProducto = codeProducto;
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

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getProcentajeDescuento() {
		return procentajeDescuento;
	}

	public void setProcentajeDescuento(int procentajeDescuento) {
		this.procentajeDescuento = procentajeDescuento;
	}

	public double getMontoExtento() {
		return montoExtento;
	}

	public void setMontoExtento(double montoExtento) {
		this.montoExtento = montoExtento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Yng_XubioSalesInvoice getXubioSalesInvoice() {
		return xubioSalesInvoice;
	}

	public void setXubioSalesInvoice(Yng_XubioSalesInvoice xubioSalesInvoice) {
		this.xubioSalesInvoice = xubioSalesInvoice;
	}

}
