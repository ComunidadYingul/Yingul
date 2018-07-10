package com.valework.yingul.service;
import java.io.File;

public interface S3Services {
	public void downloadFile(String keyName);
	public void uploadFile(String keyName, File file);
	public void uploadFile(String nombre, String extension, byte[] fis);
	public void deleteFile(String KeyName);
	public void deleteFile(String bucketName, String KeyName);
}
