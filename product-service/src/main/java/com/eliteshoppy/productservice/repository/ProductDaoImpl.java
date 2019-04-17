package com.eliteshoppy.productservice.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eliteshoppy.productservice.exception.ProductNotFoundException;
import com.eliteshoppy.productservice.model.Attribute;
import com.eliteshoppy.productservice.model.Category;
import com.eliteshoppy.productservice.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

	private static List<Product> products = new ArrayList<>();

	static {
		products.add(new Product(1, "Levis Denim", new Category(10, "Men's Cloth"), new Date(), null,
				Arrays.asList(new Attribute(20, "Color", "Blue"), new Attribute(21, "Size", "32"),
						new Attribute(22, "Price", "720"))));
		products.add(new Product(1, "Alianware", new Category(11, "Computer"), new Date(), null,
				Arrays.asList(new Attribute(20, "Color", "Black"), new Attribute(25, "CPU", "Intel"),
						new Attribute(25, "GPU", "NVidea"), new Attribute(22, "Price", "24000"),
						new Attribute(25, "RAM", "32 GB"))));
		products.add(new Product(1, "Bulb", new Category(11, "Electronics"), new Date(), null,
				Arrays.asList(new Attribute(152, "Watt", "40"), new Attribute(25, "Voltage", "230"),
						new Attribute(25, "Warranty", "1 Year"), new Attribute(22, "Price", "290"))));
	}

	@Override
	public Optional<Product> get(int productId) {
		return products.stream().filter(p -> p.getProductId() == productId).findAny();
	}

	@Override
	public void create(Product product) {
		products.add(product);
	}

	@Override
	public void update(Product product) {
		products.stream().filter(p -> p.getProductId() == product.getProductId()).findAny().ifPresent(p -> {
			p.setAttributes(product.getAttributes());
			p.setCategory(product.getCategory());
			p.setCreatedDate(product.getCreatedDate());
			p.setName(product.getName());
			p.setUpdatedDate(new Date());
		});
	}

	@Override
	public void delete(int productId) {
		Optional<Product> product = products.stream().filter(p -> p.getProductId() == productId).findAny();
		product.orElseThrow(() -> new ProductNotFoundException(String.format("Invalid product id %d", productId)));
		products.remove(product.get());
	}

}
