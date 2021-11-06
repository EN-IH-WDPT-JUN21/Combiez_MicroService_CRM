package com.ironhack.reporting.clients;

import com.ironhack.reporting.dto.SalesRep;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient("SALES-REP-SERVICE")
public interface SalesRepClient {

  @GetMapping("/salesRep/{id}")
  public SalesRep getSalesRep(@PathVariable("id") Long id);

}
