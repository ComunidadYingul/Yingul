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
public class Yng_Amenities {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "amenitiesId", nullable = false, updatable = false)
    private int amenitiesId;
	private String name;
	
	
	@OneToMany(mappedBy = "amenities", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
    private Set<Yng_PropertyAmenities> propertyAmenities = new HashSet<>();





	public int getAmenitiesId() {
		return amenitiesId;
	}


	public void setAmenitiesId(int amenitiesId) {
		this.amenitiesId = amenitiesId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<Yng_PropertyAmenities> getPropertyAmenities() {
		return propertyAmenities;
	}


	public void setPropertyAmenities(Set<Yng_PropertyAmenities> propertyAmenities) {
		this.propertyAmenities = propertyAmenities;
	}


	@Override
	public String toString() {
		return "Yng_Amenities [amenitiesId=" + amenitiesId + ", name=" + name + ", propertyAmenities="
				+ propertyAmenities + "]";
	}

  
    

}
