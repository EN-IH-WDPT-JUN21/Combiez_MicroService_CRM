package com.ironhack.reporting.clients;

import com.ironhack.reporting.enums.Status;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("OPPORTUNITY-SERVICE")
public interface OpportunityClient {

  @GetMapping("/opportunities/count-by-salesrep")
  public List<Long[]> getCountOpportunityBySalesRep();

  @GetMapping("/opportunities/count-by-salesrep/{status}")
  public List<Long[]> getCountOpportunityBySalesRepWithStatus(@PathVariable Status status);

  @GetMapping("/opportunities/count-by-product")
  public List<Object[]> getCountOpportunityByProduct();

  @GetMapping("/opportunities/count-by-product/{status}")
  public List<Object[]> getCountOpportunityByProductWithStatus(@PathVariable Status status);

  @GetMapping("/opportunities/count-by-account")
  public List<Long[]> getCountOpportunityByAccount();

  @GetMapping("/opportunities/count-by-account/{status}")
  public List<Long[]> getCountOpportunityByAccountWithStatus(@PathVariable Status status);

  @GetMapping("/opportunities/mean")
  Double getMeanProductQuantity();

  @GetMapping("/opportunities/median")
  Double getMedianProductQuantity();

  @GetMapping("/opportunities/max")
  Long getMaxProductQuantity();

  @GetMapping("/opportunities/min")
  Long getMinProductQuantity();

  @GetMapping("/opportunities/mean-by-account")
  public Double getMeanOpportunitiesPerAccount();

  @GetMapping("/opportunities/median-by-account")
  public Double getMedianOpportunitiesPerAccount();

  @GetMapping("/opportunities/max-by-account")
  public Double getMaxOpportunitiesPerAccount();

  @GetMapping("/opportunities/min-by-account")
  public Double getMinOpportunitiesPerAccount();

}
