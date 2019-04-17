package com.eliteshoppy.productservice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Document(collection = "product_collection")
public class Product {

	@Id
	private ObjectId _id;
	
	private String name;
	private Category category;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private List<Attribute> attributes;
}
