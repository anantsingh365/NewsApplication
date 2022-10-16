package com.anant.newApp;

//import com.anant.newApp.controller.NewsPresentation;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NewAppApplicationTests {

	@LocalServerPort
	int PORT;

	@Autowired
	private TestRestTemplate restTemplate;

//	@Autowired
//	private NewsPresentation controller;

	@Autowired
	private ApplicationContext context;

//	@Test
//	public void contextLoads() throws Exception {
//		assertThat(controller).isNotNull();
//	}

	@Test
	public void contextDetails(){
		Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
	}

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		String response = this.restTemplate.getForObject("http://localhost:" + PORT + "/",
				String.class);

		System.out.println(response);
//		assertThat(this.restTemplate.getForObject("http://localhost:" + PORT + "/",
//				String.class)).contains("Hello, World");
	}


}
