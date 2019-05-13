package com.eliteshoppy.productservice.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.productservice.model.ProductImage;

public interface ImageStorageService {

	ProductImage findById(String imageId);
	List<ProductImage> findByProductId(String productId);
	void deleteById(String imageId);
	void deleteByProductId(String productId);
	List<ProductImage> upload(List<MultipartFile> imageFiles, String productId);
}
