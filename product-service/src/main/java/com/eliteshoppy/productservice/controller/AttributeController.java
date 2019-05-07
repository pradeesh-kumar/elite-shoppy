package com.eliteshoppy.productservice.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliteshoppy.eliteshoppycommons.statics.UserRole;
import com.eliteshoppy.productservice.model.Attribute;
import com.eliteshoppy.productservice.service.AttributeService;

@RestController
@RequestMapping("/attribute")
public class AttributeController {

	@Autowired
	private AttributeService atrService;
	
	@GetMapping
	@RolesAllowed({UserRole.ROLE_ADMIN})
	public ResponseEntity<List<Attribute>> getAll() {
		return new ResponseEntity<>(atrService.findAll(), HttpStatus.OK);
	}
}
