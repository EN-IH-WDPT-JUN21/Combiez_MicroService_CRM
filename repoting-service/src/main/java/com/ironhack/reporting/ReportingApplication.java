package com.ironhack.reporting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ReportingApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReportingApplication.class, args);
  }

}
