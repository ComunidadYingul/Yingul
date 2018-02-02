package com.valework.yingul.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Yng_Item {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itemId", nullable = false, updatable = false)
    private Long itemId;
	private double price;
	private String money;
	private String description;
	private String name;
	private String horario;
	@Size(max = 500)
	private java.lang.String principalImage;
	private String video;
	
	@OneToOne
	private Yng_Ubication yng_Ubication;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Yng_User user;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
	@JsonBackReference(value="itemCategory")
    private Set<Yng_ItemCategory> itemCategory = new HashSet<>();
	
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    @JsonBackReference(value="itemImage")
    private Set<Yng_ItemImage> itemImage = new HashSet<>();
    private double priceNormal;
    private double priceDiscount;
    
    public Yng_Item() {
    	
    }
    
	public Yng_Item(String name) {
		super();
		this.name = name;
	}


	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public Yng_User getUser() {
		return user;
	}
	public void setUser(Yng_User user) {
		this.user = user;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Yng_Ubication getYng_Ubication() {
		return yng_Ubication;
	}
	public void setYng_Ubication(Yng_Ubication yng_Ubication) {
		this.yng_Ubication = yng_Ubication;
	}
	public Set<Yng_ItemCategory> getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(Set<Yng_ItemCategory> itemCategory) {
		this.itemCategory = itemCategory;
	}
	public Set<Yng_ItemImage> getItemImage() {
		return itemImage;
	}
	public void setItemImage(Set<Yng_ItemImage> itemImage) {
		this.itemImage = itemImage;
	}


	public java.lang.String getPrincipalImage() {
		return principalImage;
	}

	public void setPrincipalImage(java.lang.String principalImage) {
		this.principalImage = principalImage;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public double getPriceNormal() {
		return priceNormal;
	}

	public void setPriceNormal(double priceNormal) {
		this.priceNormal = priceNormal;
	}

	public double getPriceDiscount() {
		return priceDiscount;
	}

	public void setPriceDiscount(double priceDiscount) {
		this.priceDiscount = priceDiscount;
	}

	


	





}
