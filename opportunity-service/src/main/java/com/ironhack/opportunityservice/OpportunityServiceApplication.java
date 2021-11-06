package com.ironhack.opportunityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OpportunityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpportunityServiceApplication.class, args);
	}

}
