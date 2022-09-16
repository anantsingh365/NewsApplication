package com.anant.newApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class NewAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewAppApplication.class, args);
	}
}
