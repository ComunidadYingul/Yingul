package com.valework.yingul.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_Standard {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "standardId", nullable = false, updatable = false)
    private long standardId;
	private String key;
	private String value;
	private String type;
	private String description;
	@Override
	public String toString() {
		return "Yng_Standard [standardId=" + standardId + ", key=" + key + ", value=" + value + ", type=" + type
				+ ", description=" + description + "]";
	}
	public Yng_Standard() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Yng_Standard(long standardId, String key, String value, String type, String description) {
		super();
		this.standardId = standardId;
		this.key = key;
		this.value = value;
		this.type = type;
		this.description = description;
	}
	public long getStandardId() {
		return standardId;
	}
	public void setStandardId(long standardId) {
		this.standardId = standardId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	


}
