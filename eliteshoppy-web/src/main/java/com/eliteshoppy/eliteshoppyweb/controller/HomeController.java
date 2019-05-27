package com.eliteshoppy.eliteshoppyweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping({"/", "index.html"})
	public String home() {
		return "index";
	}
	
	@GetMapping({"about.html"})
	public String about() {
		return "about";
	}
	
	@GetMapping({"contact.html"})
	public String contact() {
		return "contact";
	}
	
	@GetMapping({"mens.html"})
	public String mens() {
		return "mens";
	}
	
}
