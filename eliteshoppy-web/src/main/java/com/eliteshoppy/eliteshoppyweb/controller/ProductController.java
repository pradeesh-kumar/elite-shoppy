package com.eliteshoppy.eliteshoppyweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

	@GetMapping({"product.html"})
	public String product(ModelMap model) {
		return "product";
	}
}
