package com.valework.yingul.model;



	
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;

//import java.util.HashSet;
//import java.util.Set;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
//import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Yng_Property {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "propertyId", nullable = false, updatable = false)
    private Long propertyId;
	
	@Column(name="propertyTotalArea")
	private String propertyTotalArea;
	@Column(name="propertyDuildedArea")
	private String propertyDuildedArea;
	@Column(name="propertyYear")	
	private String propertyYear;
	
	@OneToOne 
	private Yng_Item yng_Item;
	
	 	@OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
	    private Set<Yng_PropertyAmenities>  propertyAmenities= new HashSet<>();
	    
	    @OneToMany(mappedBy= "property", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	    private Set<Yng_PropertyAmbient> propertyAmbient =new HashSet<>();
	    
	

	
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyTotalArea() {
		return propertyTotalArea;
	}
	public void setPropertyTotalArea(String propertyTotalArea) {
		this.propertyTotalArea = propertyTotalArea;
	}
	public String getPropertyDuildedArea() {
		return propertyDuildedArea;
	}
	public void setPropertyDuildedArea(String propertyDuildedArea) {
		this.propertyDuildedArea = propertyDuildedArea;
	}
	public String getPropertyYear() {
		return propertyYear;
	}
	public void setPropertyYear(String propertyYear) {
		this.propertyYear = propertyYear;
	}
	public Yng_Item getYng_Item() {
		return yng_Item;
	}
	public void setYng_Item(Yng_Item yng_Item) {
		this.yng_Item = yng_Item;
	}
	public Set<Yng_PropertyAmenities> getPropertyAmenities() {
		return propertyAmenities;
	}
	public void setPropertyAmenities(Set<Yng_PropertyAmenities> propertyAmenities) {
		this.propertyAmenities = propertyAmenities;
	}
	public Set<Yng_PropertyAmbient> getPropertyAmbient() {
		return propertyAmbient;
	}
	public void setPropertyAmbient(Set<Yng_PropertyAmbient> propertyAmbient) {
		this.propertyAmbient = propertyAmbient;
	}
	@Override
	public String toString() {
		return "Yng_Property [propertyId=" + propertyId + ", propertyTotalArea=" + propertyTotalArea
				+ ", propertyDuildedArea=" + propertyDuildedArea + ", propertyYear=" + propertyYear + ", yng_Item="
				+ yng_Item + ", propertyAmenities=" + propertyAmenities + ", propertyAmbient=" + propertyAmbient + "]";
	}
	
	

}
