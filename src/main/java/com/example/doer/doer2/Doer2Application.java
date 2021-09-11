package com.example.doer.doer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring-context.xml")
public class Doer2Application {
	public static void main(String[] args) {

		SpringApplication.run(Doer2Application.class, args);
	}

}
