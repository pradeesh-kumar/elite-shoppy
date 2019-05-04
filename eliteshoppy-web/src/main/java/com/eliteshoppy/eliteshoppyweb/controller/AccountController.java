package com.eliteshoppy.eliteshoppyweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

	@GetMapping({"user-account.html"})
	public String home(ModelMap model) {
		return "user-account";
	}
}
