package com.valework.yingul.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="item_category")
public class Yng_ItemCategory {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemCategoryId;
	
	public Yng_ItemCategory(Yng_Item item, Yng_Category category) {
        this.item = item;
        this.category = category;
    }
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Yng_Item item;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Yng_Category category;
	
	
	public Yng_ItemCategory() {
		
	}
	
	public long getItemCategoryId() {
		return itemCategoryId;
	}
	public void setItemCategoryId(long itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}
	public Yng_Item getItem() {
		return item;
	}
	public void setItem(Yng_Item item) {
		this.item = item;
	}
	public Yng_Category getCategory() {
		return category;
	}
	public void setCategory(Yng_Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Yng_ItemCategory [itemCategoryId=" + itemCategoryId + ", item=" + item + ", category=" + category + "]";
	}


	/*@Override
	public String toString() {
		return "Yng_ItemCategory [itemCategoryId=" + itemCategoryId + ", item=" + item + ", category=" + category + "]";
	}*/
	
	
	
}
