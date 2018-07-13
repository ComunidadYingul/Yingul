package com.valework.yingul.service.UserServiceImpl;

import java.io.ByteArrayInputStream;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.valework.yingul.service.S3Services;
import com.valework.yingul.util.Utility;

@Service
public class S3ServicesImpl implements S3Services {
	
	private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);
	
	@Autowired
	private AmazonS3 s3client;

	@Value("${jsa.s3.bucket}")
	private String bucketName;

	@Override
	public void downloadFile(String keyName) {
		
		try {
			
            System.out.println("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(
            		bucketName, keyName));
            System.out.println("Content-Type: "  + 
            		s3object.getObjectMetadata().getContentType());
            Utility.displayText(s3object.getObjectContent());
            
        } catch (AmazonServiceException ase) {
        	logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
        	logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } catch (IOException ioe) {
        	logger.info("IOE Error Message: " + ioe.getMessage());
		}
	}


	@Override
	public void uploadFile(String keyName, File file) {
		//s3client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).disableChunkedEncoding().build());
		try {
	        s3client.putObject(new PutObjectRequest(bucketName+"/image", keyName, file));
	        
	        logger.info("===================== Upload File - Done! =====================");
	        
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
	}


	@Override
	public void uploadFile(String nombre, String extension, byte[] bI) {
		InputStream fis = new ByteArrayInputStream(bI);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(bI.length);
		metadata.setContentType("image/"+extension);
		metadata.setCacheControl("public, max-age=31536000");
		
		try {
			s3client.putObject(bucketName+"/image", nombre+"."+extension, fis, metadata);
	        //s3client.putObject(new PutObjectRequest(bucketName+"/image", nombre, fis));
	        
	        logger.info("===================== Upload File - Done! =====================");
	        
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
		
	}
	@Override
	public void deleteFile(String bucketName,String KeyName) {
		// TODO Auto-generated method stub
		try {
			//s3client.deleteBucket("img11457.webp");
			//DeleteBucketRequest deleteBucketRequest = new DeleteBucketRequest(bucketName+"/image");
			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName+"/image", KeyName);
			//delete()BucketRequest.set
			s3client.deleteObject(deleteObjectRequest);//(deleteBucketRequest );
			//s3client.putObject(bucketName+"/image", nombre+"."+extension, fis, metadata);
	        //s3client.putObject(new PutObjectRequest(bucketName+"/image", nombre, fis));
	        
	        logger.info(KeyName+"  =================== Delete File - Done! =====================");
	        
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
	}


}
