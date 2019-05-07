package com.eliteshoppy.productservice.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.eliteshoppy.productservice.model.Attribute;
import com.eliteshoppy.productservice.service.AttributeService;

@RestController
@RequestMapping("/attribute")
public class AttributeController {

	@Autowired
	private AttributeService atrService;
	
	@GetMapping
	@PermitAll
	public ResponseEntity<List<Attribute>> getAll() {
		return new ResponseEntity<>(atrService.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	@RolesAllowed({UserRole.ROLE_ADMIN})
	public ResponseEntity<Attribute> create(@NotNull @RequestBody Attribute attribute) {
		return new ResponseEntity<>(atrService.save(attribute), HttpStatus.OK);
	}
	
	@PutMapping
	@RolesAllowed({UserRole.ROLE_ADMIN})
	public ResponseEntity<Attribute> update(@NotNull @RequestBody Attribute attribute) {
		return new ResponseEntity<>(atrService.save(attribute), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@RolesAllowed({UserRole.ROLE_ADMIN})
	public ResponseEntity<HttpResponse> delete(@PathVariable String id) {
		atrService.delete(id);
		return new ResponseEntity<>(new SuccessResponse("Deleted"), HttpStatus.OK);
	}
}
