package com.eliteshoppy.productservice.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ProductAttribute implements Serializable {

	private String name;
	private List<String> values;
}
