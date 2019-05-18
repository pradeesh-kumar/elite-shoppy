package com.eliteshoppy.productservice.controller;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eliteshoppy.productservice.service.ImageService;

@Controller
@RequestMapping("/product-image")
public class ImageWebController {

	@Autowired
	private ImageService imageStorageService;
	
	@GetMapping("/{fileName}")
	@PermitAll
	public void getImage(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        imageStorageService.getFile(fileName, response);
    }
}
