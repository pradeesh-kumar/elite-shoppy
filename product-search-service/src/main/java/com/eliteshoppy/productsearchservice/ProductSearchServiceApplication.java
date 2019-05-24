package com.eliteshoppy.productsearchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.eliteshoppy.productsearchservice")
@RefreshScope
@EnableEurekaClient
public class ProductSearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductSearchServiceApplication.class, args);
	}

}
