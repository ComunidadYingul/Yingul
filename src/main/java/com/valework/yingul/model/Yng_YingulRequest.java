package com.valework.yingul.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_YingulRequest {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false, updatable = false)
    private Long yingulRequestId;
	@Column(columnDefinition = "text")
	private String Json;
	Date date;
	public Yng_YingulRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getYingulRequestId() {
		return yingulRequestId;
	}
	public void setYingulRequestId(Long yingulRequestId) {
		this.yingulRequestId = yingulRequestId;
	}
	public String getJson() {
		return Json;
	}
	public void setJson(String json) {
		Json = json;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Yng_YingulRequest [yingulRequestId=" + yingulRequestId + ", Json=" + Json + ", date=" + date + "]";
	}
	
}
