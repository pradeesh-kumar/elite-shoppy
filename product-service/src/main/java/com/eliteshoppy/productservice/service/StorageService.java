package com.eliteshoppy.productservice.service;

import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.productservice.exception.StorageException;

public interface StorageService {

	String store(MultipartFile multipartFile) throws StorageException;
}
