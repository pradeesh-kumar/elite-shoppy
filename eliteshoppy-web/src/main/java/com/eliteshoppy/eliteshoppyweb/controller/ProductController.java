package com.eliteshoppy.eliteshoppyweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

	@GetMapping({"product.html"})
	public String product() {
		return "product";
	}
	
	@GetMapping({"add-product.html"})
	public String addProduct() {
		return "add-product";
	}
	
	@GetMapping({"edit-product.html"})
	public String editProduct(ModelMap model, String productId) {
		model.addAttribute("productId", productId);
		return "edit-product";
	}
	
	@GetMapping({"attribute.html"})
	public String attribute() {
		return "attribute";
	}
	
	@GetMapping({"single.html"})
	public String single(ModelMap model, String productId) {
		model.addAttribute("productId", productId);
		return "single";
	}
}
