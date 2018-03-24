package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Yng_ResetPassword {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "resetpasswordId", nullable = false, updatable = false)
    private Long resetpasswordId;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Yng_User user;
	
    public Yng_ResetPassword() {
    	
    }

	public Long getResetpasswordId() {
		return resetpasswordId;
	}

	public void setResetpasswordId(Long resetpasswordId) {
		this.resetpasswordId = resetpasswordId;
	}

	public Yng_User getUser() {
		return user;
	}

	public void setUser(Yng_User user) {
		this.user = user;
	}
    
}
