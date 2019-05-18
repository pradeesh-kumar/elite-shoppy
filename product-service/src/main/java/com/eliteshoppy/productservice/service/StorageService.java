package com.eliteshoppy.productservice.service;

import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.productservice.exception.StorageException;
import com.google.cloud.storage.Blob;

public interface StorageService {

	String store(MultipartFile multipartFile) throws StorageException;
	Blob retrieve(String fileName) throws StorageException;
}
