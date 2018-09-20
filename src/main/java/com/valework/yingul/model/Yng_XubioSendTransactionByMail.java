package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_XubioSendTransactionByMail {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "xubioSendTransactionByMailId", nullable = false, updatable = false)
    private Long xubioSendTransactionByMailId;
	
	private Long transaccionId;
	private String destinatarios;
	private String copiaCon;
	private String copiaConOtro;
	private String asunto;
	private String cuerpo;
	
	public Yng_XubioSendTransactionByMail() {
		super();
	}

	public Long getXubioSendTransactionByMailId() {
		return xubioSendTransactionByMailId;
	}

	public void setXubioSendTransactionByMailId(Long xubioSendTransactionByMailId) {
		this.xubioSendTransactionByMailId = xubioSendTransactionByMailId;
	}

	public Long getTransaccionId() {
		return transaccionId;
	}

	public void setTransaccionId(Long transaccionId) {
		this.transaccionId = transaccionId;
	}

	public String getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}

	public String getCopiaCon() {
		return copiaCon;
	}

	public void setCopiaCon(String copiaCon) {
		this.copiaCon = copiaCon;
	}

	public String getCopiaConOtro() {
		return copiaConOtro;
	}

	public void setCopiaConOtro(String copiaConOtro) {
		this.copiaConOtro = copiaConOtro;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

}
