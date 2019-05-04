package com.eliteshoppy.eliteshoppyweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.eliteshoppy.eliteshoppyweb")
public class EliteshoppyWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(EliteshoppyWebApplication.class, args);
	}

}
