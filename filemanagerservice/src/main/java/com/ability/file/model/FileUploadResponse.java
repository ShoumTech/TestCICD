package com.ability.file.model;

public class FileUploadResponse {

	private String fileName;
	private String fileDownloadUri;
	private String employeeId;
	private long size;

	public FileUploadResponse(String fileName, String fileDownloadUri, String fileType, long size) {
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.employeeId = fileType;
		this.size = size;
	}

	public FileUploadResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public String getFileType() {
		return employeeId;
	}

	public void setFileType(String fileType) {
		this.employeeId = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}