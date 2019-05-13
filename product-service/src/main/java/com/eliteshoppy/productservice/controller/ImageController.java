package com.eliteshoppy.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.productservice.model.ProductImage;
import com.eliteshoppy.productservice.service.ImageStorageService;

@RestController
@RequestMapping("/product/image")
@Validated
public class ImageController {
	
	@Autowired
	private ImageStorageService imageStorageService;

	@PostMapping("/upload")
	public ResponseEntity<List<ProductImage>> uploadFile(@RequestParam("productImage") List<MultipartFile> imageFiles, String productId) {
		return new ResponseEntity<>(imageStorageService.upload(imageFiles, productId), HttpStatus.CREATED);
	}
}
