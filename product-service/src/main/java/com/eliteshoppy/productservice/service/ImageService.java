package com.eliteshoppy.productservice.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.productservice.model.ProductImage;

public interface ImageService {

	ProductImage findById(String imageId);
	void getFile(String fileName, HttpServletResponse response);
	List<ProductImage> findByProductId(String productId);
	void deleteById(String imageId);
	void deleteByProductId(String productId);
	List<ProductImage> upload(List<MultipartFile> imageFiles, String productId);
}
