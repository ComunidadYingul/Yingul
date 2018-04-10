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

import org.springframework.beans.factory.annotation.Value;

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
	//@Column(name="productLength", columnDefinition="default '0'")
	@Value("${some.key:0}")
	private int productLength=0;
	//@Column(name="productWidth", columnDefinition="default '0'")
	@Value("${some.key:0}")
	private int productWidth=0;
	//@Column(name="productHeight", columnDefinition="default '0'")
	@Value("${some.key:0}")
	private int productHeight=0;
	//@Column(name="productWeight", columnDefinition="default '0'")
	@Value("${some.key:0}")
	private int productWeight=0;
	@OneToOne 
	private Yng_Item yng_Item;



	public Yng_Product() {
		super();
		// TODO Auto-generated constructor stub
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

	public int getProductLength() {
		return productLength;
	}

	public void setProductLength(int productLength) {
		this.productLength = productLength;
	}

	public int getProductWidth() {
		return productWidth;
	}

	public void setProductWidth(int productWidth) {
		this.productWidth = productWidth;
	}

	public int getProductHeight() {
		return productHeight;
	}

	public void setProductHeight(int productHeight) {
		this.productHeight = productHeight;
	}

	public int getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(int productWeight) {
		this.productWeight = productWeight;
	}

	public Yng_Item getYng_Item() {
		return yng_Item;
	}

	public void setYng_Item(Yng_Item yng_Item) {
		this.yng_Item = yng_Item;
	}

}
