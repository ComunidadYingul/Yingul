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
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Yng_Category{
	@Id
	//si no funciona comentar las dos lineas debajo de esta 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryId", nullable = false, updatable = false)
	private Long categoryId;
	private String name;
	private int level;
	private String itemType;
	private Long fatherId;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
    private Set<Yng_ItemCategory> itemCategory = new HashSet<>();
    
    public Yng_Category() {
    	
    }

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getFatherId() {
		return fatherId;
	}

	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public Set<Yng_ItemCategory> getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(Set<Yng_ItemCategory> itemCategory) {
		this.itemCategory = itemCategory;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	/*@Override
	public String toString() {
		return "Yng_Category [categoryId=" + categoryId + ", name=" + name + ", level=" + level + ", itemType="
				+ itemType + ", yng_Category=" + yng_Category + ", itemCategory=" + itemCategory + "]";
	}*/

	@Override
	public String toString() {
		return "Yng_Category [categoryId=" + categoryId + ", name=" + name + ", level=" + level + ", itemType="
				+ itemType + ", fatherId=" + fatherId + ", itemCategory=" + itemCategory + "]";
	}


	
}
