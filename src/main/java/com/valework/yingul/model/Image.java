package com.valework.yingul.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class Image {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "imageId", nullable = false, updatable = false)
	private int imageId;
	@Column(name="photo_blob")
	@Type(type="org.hibernate.type.BinaryType") 
	private byte[] photoBlob;

	public byte[] getPhotoBlob() {
		return photoBlob;
	}

	public void setPhotoBlob(byte[] photoBlob) {
		this.photoBlob = photoBlob;
	}

	@Override
	public String toString() {
		return "Image [photoBlob=" + Arrays.toString(photoBlob) + "]";
	}
	
}
