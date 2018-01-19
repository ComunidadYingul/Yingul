package com.valework.yingul.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="prop_amenities")
public class Yng_PropertyAmenities {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long propertyAmenities;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id")
	@JsonBackReference(value="property_id")
    private Yng_Property property;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "amenities_id")	
    private Yng_Amenities amenities;

	public long getPropertyAmenities() {
		return propertyAmenities;
	}

	public void setPropertyAmenities(long propertyAmenities) {
		this.propertyAmenities = propertyAmenities;
	}

	public Yng_Property getProperty() {
		return property;
	}

	public void setProperty(Yng_Property property) {
		this.property = property;
	}

	@Override
	public String toString() {
		return "Yng_PropertyAmenities [propertyAmenities=" + propertyAmenities + ", property=" + property
				+ ", amenities=" + amenities + "]";
	}

	public Yng_Amenities getAmenities() {
		return amenities;
	}

	public void setAmenities(Yng_Amenities amenities) {
		this.amenities = amenities;
	}

	

	
	
	
	

}
