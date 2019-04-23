package com.eliteshoppy.productservice.model;

import java.math.BigDecimal;
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
	private String category;
	private BigDecimal price;
	private int availableQuantity;
	private boolean active;
	private ObjectId ownerId;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private List<Attribute> attributes;
}
