package com.eliteshoppy.eliteshoppyweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication(scanBasePackages = "com.eliteshoppy.eliteshoppyweb")
@RefreshScope
public class EliteshoppyWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(EliteshoppyWebApplication.class, args);
	}

}
