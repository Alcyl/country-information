package com.mario.countrycatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CountryCatalogApplication {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
//	@Bean
//	public WebClient.Builder getWebClientBuilder() { return WebClient.builder(); }

	public static void main(String[] args) {
		SpringApplication.run(CountryCatalogApplication.class, args);
	}

}
