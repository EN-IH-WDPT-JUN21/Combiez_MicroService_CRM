package com.ironhack.microservice_homework_account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroserviceHomeworkAccountApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroserviceHomeworkAccountApplication.class, args);
  }

}
