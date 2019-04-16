package com.eliteshoppy.productservice.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Product {

	private int productId;
	private String name;
	private Category category;
	private Date createdDate;
	private Date updatedDate;
	private List<Attribute> attributes;
}
