package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
public class Yng_ItemImage {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itemImageId", nullable = false, updatable = false)
    private Long itemImageId;
	private java.lang.String image;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Yng_Item item;
	
	public Yng_ItemImage() {
		
	}
	
	public Yng_ItemImage(String image, Yng_Item item) {
		this.image = image;
		this.item = item;
	}
	public Long getItemImageId() {
		return itemImageId;
	}
	public void setItemImageId(Long itemImageId) {
		this.itemImageId = itemImageId;
	}
	public java.lang.String getImage() {
		return image;
	}
	public void setImage(java.lang.String image) {
		this.image = image;
	}
	public Yng_Item getItem() {
		return item;
	}
	public void setItem(Yng_Item item) {
		this.item = item;
	}
	@Override
	public String toString() {
		return "Yng_ItemImage [itemImageId=" + itemImageId + ", image=" + image + ", item=" + item + "]";
	}

	
}
