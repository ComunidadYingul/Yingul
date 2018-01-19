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
public class Yng_Shipping {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shippingId", nullable = false, updatable = false)
    private Long shippingId;
	private String typeShipping;
	//private
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "envio_id")
    private Yng_Envio yng_envio;

	@Override
	public String toString() {
		return "Yng_Shipping [shippingId=" + shippingId + ", typeShipping=" + typeShipping + ", yng_envio=" + yng_envio
				+ "]";
	}

	public Long getShippingId() {
		return shippingId;
	}

	public void setShippingId(Long shippingId) {
		this.shippingId = shippingId;
	}

	public String getTypeShipping() {
		return typeShipping;
	}

	public void setTypeShipping(String typeShipping) {
		this.typeShipping = typeShipping;
	}

	public Yng_Envio getYng_envio() {
		return yng_envio;
	}

	public void setYng_envio(Yng_Envio yng_envio) {
		this.yng_envio = yng_envio;
	}
	
	

}
