package com.eliteshoppy.productsearchservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(indexName = "product_index", type = "product")
public class Product {

	@Id
	private String id;

	private String name;
	private String category;
	private String description;
	private BigDecimal price;
	private BigDecimal offerPrice;
	private int availableQuantity;
	private boolean active;
	private String ownerId;
	private String idealFor;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private List<ProductAttribute> attributes;
}
