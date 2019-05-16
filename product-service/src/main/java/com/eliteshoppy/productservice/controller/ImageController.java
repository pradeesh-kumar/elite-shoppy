package com.eliteshoppy.productservice.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.eliteshoppycommons.statics.UserRole;
import com.eliteshoppy.productservice.model.ProductImage;
import com.eliteshoppy.productservice.service.ImageService;

@RestController
@RequestMapping("/image")
@Validated
public class ImageController {
	
	@Autowired
	private ImageService imageStorageService;

	@PostMapping("/upload")
	@RolesAllowed({ UserRole.ROLE_ADMIN, UserRole.ROLE_SELLER })
	public ResponseEntity<List<ProductImage>> uploadFile(
			@RequestParam("productImages") List<MultipartFile> productImages,
			@RequestParam("productId") String productId) {
		return new ResponseEntity<>(imageStorageService.upload(productImages, productId), HttpStatus.CREATED);
	}
}
