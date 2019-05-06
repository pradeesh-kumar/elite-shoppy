package com.eliteshoppy.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliteshoppy.productservice.model.Attribute;
import com.eliteshoppy.productservice.repository.AttributeRepository;

@Service
public class AttributeServiceImpl implements AttributeService {

	@Autowired
	private AttributeRepository atrRepo;

	@Override
	public List<Attribute> findAll() {
		return atrRepo.findAll();
	}
}
