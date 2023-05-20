
package com.ability.file.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ability.file.exception.FileProcessingException;
import com.ability.file.model.FileUploadResponse;
import com.ability.file.service.api.FileManagerServiceApi;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class FileManagerServiceImpl implements FileManagerServiceApi {


	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${aws.s3.bucket.name}")
	private  String S3_BUCKER_NAME;
	
	private String ROOT_FOLDER = "documents";

	@Override
	public FileUploadResponse storeFile(MultipartFile file, String employeeId) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileUploadResponse fileResponse = new FileUploadResponse();
		try {
			if (fileName.contains("..")) {
				throw new FileProcessingException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			ObjectMetadata data = new ObjectMetadata();
		    data.setContentType(file.getContentType());
		    data.setContentLength(file.getSize());
		    String filePath = ROOT_FOLDER+"/"+employeeId+"/"+file.getOriginalFilename();
		    PutObjectResult objectResult = s3Client.putObject(S3_BUCKER_NAME, filePath, file.getInputStream(), data);
			if(objectResult == null) {
				throw new FileProcessingException("FILE_UPLOAD_FAILED");
			}
		    fileResponse = new FileUploadResponse(fileName,filePath, employeeId,
					file.getSize());
		} catch (IOException ex) {
			throw new FileProcessingException("Could not store file " + fileName + ". Please try again!", ex);
		}
		return fileResponse;
	}

	@Override
	public S3Object loadFileAsResource(String fileName, String employeeId) {
		try {
			String filePath = ROOT_FOLDER+"/"+employeeId+"/"+fileName;
			S3Object s3object = s3Client.getObject(S3_BUCKER_NAME, filePath);
			if(s3object == null) {
				throw new FileProcessingException("FILE_IS_EMPTY");
			}
			return s3object;
			
		} catch (FileProcessingException ex) {
			throw new FileProcessingException("File not found " + fileName, ex);
		}
	}
}