package com.eliteshoppy.productservice.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.eliteshoppycommons.httpresponse.HttpResponse;
import com.eliteshoppy.eliteshoppycommons.httpresponse.SuccessResponse;
import com.eliteshoppy.productservice.model.ProductImage;
import com.eliteshoppy.productservice.service.ImageService;

@RestController
@RequestMapping("/image")
@Validated
public class ImageController {
	
	@Autowired
	private ImageService imageStorageService;

	@PostMapping("/upload")
	@PermitAll
	public ResponseEntity<List<ProductImage>> uploadFile(
			@RequestParam("productImages") List<MultipartFile> productImages,
			@RequestParam("productId") String productId) {
		return new ResponseEntity<>(imageStorageService.upload(productImages, productId), HttpStatus.CREATED);
	}
	
	@GetMapping("/{productId}")
	@PermitAll
	public ResponseEntity<List<ProductImage>> getImages(@PathVariable("productId") String productId) {
        return new ResponseEntity<>(imageStorageService.findByProductId(productId), HttpStatus.OK);
    }
	
	@GetMapping("/any/{productId}")
	@PermitAll
	public ResponseEntity<ProductImage> findAny(@PathVariable("productId") String productId) {
        return new ResponseEntity<>(imageStorageService.findOneByProductId(productId), HttpStatus.OK);
    }
	
	@GetMapping("/download/{fileName}")
	@PermitAll
	public void downloadImage(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        imageStorageService.getFile(fileName, response);
    }
	
	@DeleteMapping("/{imgId}")
	@PermitAll
	public ResponseEntity<HttpResponse> deleteImage(@PathVariable String imgId) {
		imageStorageService.deleteById(imgId);
		return new ResponseEntity<>(new SuccessResponse("Image has been deleted."), HttpStatus.OK);
	}
}
