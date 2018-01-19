package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Yng_Person {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "personId", nullable = false, updatable = false)
    private Long personId;
	private String name;
	private String lastname;
	private boolean isBusiness;
	@OneToOne(fetch = FetchType.EAGER)
	private Yng_User yng_User;
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public boolean isBusiness() {
		return isBusiness;
	}
	public void setBusiness(boolean isBusiness) {
		this.isBusiness = isBusiness;
	}
	public Yng_User getYng_User() {
		return yng_User;
	}
	public void setYng_User(Yng_User yng_User) {
		this.yng_User = yng_User;
	}
	@Override
	public String toString() {
		return "Yng_Person [personId=" + personId + ", name=" + name + ", lastname=" + lastname + ", isBusiness="
				+ isBusiness + ", yng_User=" + yng_User + "]";
	}
	
	

}
