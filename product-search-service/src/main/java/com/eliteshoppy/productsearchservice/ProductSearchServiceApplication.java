package com.eliteshoppy.productsearchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(scanBasePackages = "com.eliteshoppy.productsearchservice")
@RefreshScope
@EnableEurekaClient
@EnableElasticsearchRepositories(basePackages = "com.eliteshoppy.productsearchservice.repository")
public class ProductSearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductSearchServiceApplication.class, args);
	}

}
