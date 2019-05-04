package com.eliteshoppy.productservice.controller;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliteshoppy.eliteshoppycommons.httpresponse.HttpResponse;
import com.eliteshoppy.eliteshoppycommons.httpresponse.SuccessResponse;
import com.eliteshoppy.eliteshoppycommons.statics.UserRole;
import com.eliteshoppy.productservice.model.Product;
import com.eliteshoppy.productservice.service.ProductService;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/{productId}")
	@PermitAll
	public ResponseEntity<Product> getProduct(@PathVariable String productId) {
		return new ResponseEntity<>(productService.findById(productId), HttpStatus.OK);
	}
	
	@PostMapping
	@RolesAllowed({UserRole.ROLE_ADMIN, UserRole.ROLE_SELLER})
	public ResponseEntity<Product> createProduct(@NotNull @RequestBody Product product) {
		return new ResponseEntity<>(productService.create(product), HttpStatus.CREATED);
	}
	
	@PutMapping
	@RolesAllowed({UserRole.ROLE_ADMIN, UserRole.ROLE_SELLER})
	public ResponseEntity<HttpResponse> updateProduct(@NotNull @RequestBody Product product) {
		productService.update(product);
		return new ResponseEntity<>(new SuccessResponse("Product has been updated."), HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}")
	@RolesAllowed({UserRole.ROLE_ADMIN, UserRole.ROLE_SELLER})
	public ResponseEntity<HttpResponse> deleteProduct(@PathVariable String productId) {
		productService.delete(productId);
		return new ResponseEntity<>(new SuccessResponse("Product has been updated."), HttpStatus.OK);
	}
	
}
