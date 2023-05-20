package com.ability.file.service.api;

import org.springframework.web.multipart.MultipartFile;

import com.ability.file.model.FileUploadResponse;
import com.amazonaws.services.s3.model.S3Object;

public interface FileManagerServiceApi {

	public FileUploadResponse storeFile(MultipartFile file, String employeeId);

	public S3Object loadFileAsResource(String fileName, String employeeId);

}