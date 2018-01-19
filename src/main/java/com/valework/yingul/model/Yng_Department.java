package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Yng_Department {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "departmentId", nullable = false, updatable = false)
	private int departmentId;
	private String name;
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Yng_Department [departmentId=" + departmentId + ", name=" + name + "]";
	}
	
}
