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
@Table(name="prop_ambient")
public class Yng_PropertyAmbient {
	
	

 @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long propertyAmbientId;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id")
	@JsonBackReference(value="property_id")
    private Yng_Property property;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ambient_id")	
    private Yng_Ambient ambient;

	public Yng_PropertyAmbient() {
		
	}

	
	
	public Yng_PropertyAmbient(Yng_Property property, Yng_Ambient ambient) {
		
		this.property = property;
		this.ambient = ambient;
	}



	public Yng_Property getProperty() {
		return property;
	}



	public void setProperty(Yng_Property property) {
		this.property = property;
	}



	public long getPropertyAmbientId() {
		return propertyAmbientId;
	}

	public void setPropertyAmbientId(long propertyAmbientId) {
		this.propertyAmbientId = propertyAmbientId;
	}



	public Yng_Ambient getAmbient() {
		return ambient;
	}

	public void setAmbient(Yng_Ambient ambient) {
		this.ambient = ambient;
	}



	@Override
	public String toString() {
		return "Yng_PropertyAmbient [propertyAmbientId=" + propertyAmbientId + ", property=" + property + ", ambient="
				+ ambient + "]";
	}

	
	
	
	
 
 
}
