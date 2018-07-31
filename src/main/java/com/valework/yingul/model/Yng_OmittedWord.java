package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_OmittedWord {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "omittedWordId", nullable = false, updatable = false)
    private int omittedWordId;
	private String word;
    
    public Yng_OmittedWord() {
    	
    }

	public int getOmittedWordId() {
		return omittedWordId;
	}

	public void setOmittedWordId(int omittedWordId) {
		this.omittedWordId = omittedWordId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
    
}
