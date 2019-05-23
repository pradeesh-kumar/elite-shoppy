package com.eliteshoppy.productservice.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Document(collection = "product_image")
public class ProductImage implements Serializable {

	@Id
	private String id;
	
	private String productId;
	private String path;
}
