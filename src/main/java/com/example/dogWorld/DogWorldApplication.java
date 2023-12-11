package com.example.dogWorld;

import com.example.dogWorld.Crawling.Crawling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DogWorldApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DogWorldApplication.class, args);

		Crawling crawling = new Crawling();
		crawling.process();
	}

}
