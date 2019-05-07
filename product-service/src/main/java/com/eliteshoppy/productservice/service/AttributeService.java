package com.eliteshoppy.productservice.service;

import java.util.List;

import com.eliteshoppy.productservice.model.Attribute;

public interface AttributeService {

	List<Attribute> findAll();
	Attribute save(Attribute attribute);
	void delete(String id);
}
