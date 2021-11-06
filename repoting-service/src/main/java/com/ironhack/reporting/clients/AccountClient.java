package com.ironhack.reporting.clients;

import com.ironhack.reporting.dto.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient("ACCOUNT-SERVICE")
public interface AccountClient {
  @GetMapping("account/{id}")
  public Account getById(@PathVariable Long id);

  @GetMapping("account/employeeCount/mean")
  public Double getMeanEmployeeCount();

  @GetMapping("account/employeeCount/median")
  public Double getMedianEmployeeCount();

  @GetMapping("account/employeeCount/min")
  public Integer getMinEmployeeCount();

  @GetMapping("account/employeeCount/max")
  public Integer getMaxEmployeeCount();

}
