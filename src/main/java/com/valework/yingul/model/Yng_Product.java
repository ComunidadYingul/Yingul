package com.valework.yingul.model;

//import java.util.HashSet;
//import java.util.Set;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Yng_Product {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productId", nullable = false, updatable = false)
    private Long productId;
	
	
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Column(name="productCondition")
	private String productCondition;
	@Column(name="productSaleConditions")
	private String productSaleConditions;
	@Column(name="productQuantity")	
	private String productQuantity;
	@Column(name="productFormDelivery")	
	private String productFormDelivery;
	@Column(name="productPaymentMethod")	
	private String productPaymentMethod;
	@Column(name="productWarranty")	
	private String productWarranty="";
	@Column(name="productPagoEnvio")
	private String productPagoEnvio="";
	private String productPeso="";
	private String producVolumen="";
	//private String 
	
	@OneToOne 
	private Yng_Item yng_Item;

	

	public String getProductPeso() {
		return productPeso;
	}

	public void setProductPeso(String productPeso) {
		this.productPeso = productPeso;
	}

	public String getProducVolumen() {
		return producVolumen;
	}

	public void setProducVolumen(String producVolumen) {
		this.producVolumen = producVolumen;
	}

	public String getProductCondition() {
		return productCondition;
	}

	public void setProductCondition(String productCondition) {
		this.productCondition = productCondition;
	}

	public String getProductSaleConditions() {
		return productSaleConditions;
	}

	public void setProductSaleConditions(String productSaleConditions) {
		this.productSaleConditions = productSaleConditions;
	}

	public String getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductFormDelivery() {
		return productFormDelivery;
	}

	public void setProductFormDelivery(String productFormDelivery) {
		this.productFormDelivery = productFormDelivery;
	}

	public String getProductPaymentMethod() {
		return productPaymentMethod;
	}

	public void setProductPaymentMethod(String productPaymentMethod) {
		this.productPaymentMethod = productPaymentMethod;
	}

	public String getProductWarranty() {
		return productWarranty;
	}

	public void setProductWarranty(String productWarranty) {
		this.productWarranty = productWarranty;
	}

	public String getProductPagoEnvio() {
		return productPagoEnvio;
	}

	public void setProductPagoEnvio(String productPagoEnvio) {
		this.productPagoEnvio = productPagoEnvio;
	}

	public Yng_Item getYng_Item() {
		return yng_Item;
	}

	public void setYng_Item(Yng_Item yng_Item) {
		this.yng_Item = yng_Item;
	}

	@Override
	public String toString() {
		return "Yng_Product [productId=" + productId + ", productCondition=" + productCondition
				+ ", productSaleConditions=" + productSaleConditions + ", productQuantity=" + productQuantity
				+ ", productFormDelivery=" + productFormDelivery + ", productPaymentMethod=" + productPaymentMethod
				+ ", productWarranty=" + productWarranty + ", productPagoEnvio=" + productPagoEnvio + ", productPeso="
				+ productPeso + ", producVolumen=" + producVolumen + ", yng_Item=" + yng_Item + "]";
	}

	public Yng_Product() {
		super();
	}


	
	
	
	
	
	
    

}
