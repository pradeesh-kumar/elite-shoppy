package com.eliteshoppy.productservice.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.productservice.exception.StorageException;
import com.google.cloud.storage.Blob;

public interface StorageService {

	String store(MultipartFile multipartFile) throws StorageException;
	Blob retrieve(String fileName) throws StorageException;
	void delete(String fileName);
	void deleteMany(List<String> fileNames);
}
