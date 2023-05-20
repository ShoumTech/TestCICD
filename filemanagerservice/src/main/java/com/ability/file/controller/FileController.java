package com.ability.file.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ability.file.model.FileUploadResponse;
import com.ability.file.service.api.FileManagerServiceApi;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api/file/")
@Slf4j
public class FileController {

	@Autowired
	private FileManagerServiceApi fileStorageService;

	@PostMapping("/upload")
	public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file , @RequestParam("employeeId") String employeeId) {
		FileUploadResponse response = fileStorageService.storeFile(file, employeeId);
		return response;
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName, HttpServletRequest request,  @RequestParam("employeeId") String employeeId) {
		// Load file as Resource
		S3Object s3object = fileStorageService.loadFileAsResource(fileName, employeeId);
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		Resource resource = new InputStreamResource(inputStream);
		// Try to determine file's content type
		String contentType = null;
		contentType = s3object.getObjectMetadata().getContentType();
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
