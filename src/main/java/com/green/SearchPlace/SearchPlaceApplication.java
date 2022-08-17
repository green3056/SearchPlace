package com.green.SearchPlace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SearchPlaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchPlaceApplication.class, args);
	}

}
