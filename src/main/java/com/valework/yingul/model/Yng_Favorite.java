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
public class Yng_Favorite {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "favoriteId", nullable = false, updatable = false)
    private Long favoriteId;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Yng_User user;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Yng_Item item;
	
	public Yng_Favorite() {
		
	}

	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Yng_User getUser() {
		return user;
	}

	public void setUser(Yng_User user) {
		this.user = user;
	}

	public Yng_Item getItem() {
		return item;
	}

	public void setItem(Yng_Item item) {
		this.item = item;
	}

}
