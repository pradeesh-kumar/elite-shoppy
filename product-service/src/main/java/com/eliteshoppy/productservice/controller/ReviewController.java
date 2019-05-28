package com.eliteshoppy.productservice.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliteshoppy.productservice.model.Review;
import com.eliteshoppy.productservice.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;

	@GetMapping("/{productId}")
	@PermitAll
	public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable String productId) {
		return new ResponseEntity<>(reviewService.findByProductId(productId), HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Review> addReview(@NotNull @RequestBody Review review) {
		return new ResponseEntity<>(reviewService.create(review), HttpStatus.CREATED);
	}
}
