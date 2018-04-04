package com.valework.yingul.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Yng_Notification {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long notificationId;
	private String url;
	private String answer;
	private String date;
	private String status;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Yng_User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private Yng_User seller;
	
	public Yng_Notification() {
		
	}
	
	public Yng_User getUser() {
		return user;
	}

	public void setUser(Yng_User user) {
		this.user = user;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Yng_User getSeller() {
		return seller;
	}

	public void setSeller(Yng_User seller) {
		this.seller = seller;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
		
}
