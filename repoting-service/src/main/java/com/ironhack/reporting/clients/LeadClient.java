package com.ironhack.reporting.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("LEAD-SERVICE")
public interface LeadClient {
  @GetMapping("/lead/count-by-salesrep")
  public List<Long[]> getCountLeadBySalesRep();
}
