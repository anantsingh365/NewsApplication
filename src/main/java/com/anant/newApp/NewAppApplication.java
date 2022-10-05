package com.anant.newApp;

import com.anant.newApp.controller.scopeTesting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class NewAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewAppApplication.class, args);
	}
	@Bean
	public scopeTesting getscopeTestingObject(){
		return new scopeTesting();
	}
}
