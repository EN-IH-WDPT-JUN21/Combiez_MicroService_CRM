package com.ironhack.reporting.controller;

import com.ironhack.reporting.clients.AccountClient;
import com.ironhack.reporting.clients.OpportunityClient;
import com.ironhack.reporting.enums.Status;
import com.ironhack.reporting.service.ReportingService;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportingController {

  @Autowired
  private ReportingService reportingService;

  private final AccountClient accountClient;
  private final OpportunityClient opportunityClient;

  public ReportingController(AccountClient accountClient, OpportunityClient opportunityClient) {
    this.accountClient = accountClient;
    this.opportunityClient = opportunityClient;
  }

  Logger logger = LoggerFactory.getLogger("ReportingApplication.class");

  //BySalesRep#1: A count of Leads by SalesRep
  @GetMapping("/leads/count-by-salesrep")
  @Retry(name = "lead-service", fallbackMethod = "getObjectFallbackMethod")
  public List<Object[]> getCountLeadBySalesRep() {
    return reportingService.getCountLeadBySalesRep();
  }

  //BySalesRep#2: A count of all Opportunities by SalesRep
  @GetMapping("/opportunities/count-by-salesrep")
  @Retry(name = "opportunity-service", fallbackMethod = "getObjectFallbackMethod")
  public List<Object[]> getCountOpportunityBySalesRep() {
    return reportingService.getCountOpportunityBySalesRep();
  }

  //BySalesRep#3: A count of all CLOSED_WON Opportunities by SalesRep
  //BySalesRep#4: A count of all CLOSED_LOST Opportunities by SalesRep
  //BySalesRep#5: A count of all OPEN Opportunities by SalesRep
  @GetMapping("/opportunities/count-by-salesrep/{status}")
  @Retry(name = "opportunity-service", fallbackMethod = "getObjectFallbackMethod")
  public List<Object[]> getCountOpportunityBySalesRepWithStatus(@PathVariable Status status) {
    return reportingService.getCountOpportunityBySalesRepWithStatus(status);
  }

  //ByProduct#1: A count of all Opportunities by the product
  //ByProduct#2: A count of all CLOSED_WON Opportunities by the product
  //ByProduct#3: A count of all CLOSED_LOST Opportunities by the product
  //ByProduct#4: A count of all OPEN Opportunities by the product
  @GetMapping("/opportunities/count-by-product")
  @Retry(name = "opportunity-service", fallbackMethod = "getObjectFallbackMethod")
  public List<Object[]> getCountOpportunityByProduct() {
    return reportingService.getCountOpportunityByProduct();
  }

  @GetMapping("/opportunities/count-by-product/{status}")
  @Retry(name = "opportunity-service", fallbackMethod = "getObjectFallbackMethod")
  public List<Object[]> getCountOpportunityByProductWithStatus(@PathVariable Status status) {
    return reportingService.getCountOpportunityByProductWithStatus(status);
  }

  //ByCountry#1: A count of all Opportunities by country
  //ByCountry#2: A count of all CLOSED_WON Opportunities by country
  //ByCountry#3: A count of all CLOSED_LOST Opportunities by country
  //ByCountry#4: A count of all OPEN Opportunities by country
  @GetMapping("/opportunities/count-by-country/")
  @Retry(name = "opportunity-service", fallbackMethod = "getObjectFallbackMethod")
  public List<Object[]> getCountOpportunityByCountry() {
    return reportingService.getCountOpportunityByCountry();
  }

  @GetMapping("/opportunities/count-by-country/{status}")
  @Retry(name = "opportunity-service", fallbackMethod = "getObjectFallbackMethod")
  public List<Object[]> getCountOpportunityByCountryWithStatus(@PathVariable Status status) {
    return reportingService.getCountOpportunityByCountryWithStatus(status);
  }

  //ByCity#1: A count of all Opportunities by the city
  //ByCity#2: A count of all CLOSED_WON Opportunities by the city
  //ByCity#3: A count of all CLOSED_LOST Opportunities by the city
  //ByCity#4: A count of all OPEN Opportunities by the city
  @GetMapping("/opportunities/count-by-city/")
  @Retry(name = "opportunity-service", fallbackMethod = "getObjectFallbackMethod")
  public List<Object[]> getCountOpportunityByCity() {
    return reportingService.getCountOpportunityByCity();
  }

  @GetMapping("/opportunities/count-by-city/{status}")
  @Retry(name = "opportunity-service", fallbackMethod = "getObjectFallbackMethod")
  public List<Object[]> getCountOpportunityByCityWithStatus(@PathVariable Status status) {
    return reportingService.getCountOpportunityByCityWithStatus(status);
  }

  //ByIndustry#1: A count of all Opportunities by industry
  //ByIndustry#2: A count of all CLOSED_WON Opportunities by industry
  //ByIndustry#3: A count of all CLOSED_LOST Opportunities by industry
  //ByIndustry#4: A count of all OPEN Opportunities by industry
  @GetMapping("/opportunities/count-by-industry/")
  @Retry(name = "opportunity-service", fallbackMethod = "getObjectFallbackMethod")
  public List<Object[]> getCountOpportunityByIndustry() {
    return reportingService.getCountOpportunityByIndustry();
  }

  @GetMapping("/opportunities/count-by-industry/{status}")
  @Retry(name = "opportunity-service", fallbackMethod = "getObjectFallbackMethod")
  public List<Object[]> getCountOpportunityByIndustryWithStatus(@PathVariable Status status) {
    return reportingService.getCountOpportunityByIndustryWithStatus(status);
  }

  //Quantity#1: The mean quantity of products order
  @GetMapping("/opportunities/mean")
  @Retry(name = "opportunity-service", fallbackMethod = "getDoubleFallbackMethod")
  public Double getMeanProductQuantity() {
    return opportunityClient.getMeanProductQuantity();
  }

  //Quantity#2: The median quantity of products order
  @GetMapping("/opportunities/median")
  @Retry(name = "opportunity-service", fallbackMethod = "getDoubleFallbackMethod")
  Double getMedianProductQuantity() {
    return opportunityClient.getMedianProductQuantity();
  }

  //Quantity#3: The max quantity of products order
  @GetMapping("/opportunities/max")
  @Retry(name = "opportunity-service", fallbackMethod = "getLongFallbackMethod")
  public Long getMaxProductQuantity() {
    return opportunityClient.getMaxProductQuantity();
  }

  //Quantity#4: The min quantity of products order
  @GetMapping("/opportunities/min")
  @Retry(name = "opportunity-service", fallbackMethod = "getLongFallbackMethod")
  public Long getMinProductQuantity() {
    return opportunityClient.getMinProductQuantity();
  }

  //#1: The mean number of Opportunities associated with an Account
  @GetMapping("/opportunities/mean-by-account/")
  @Retry(name = "opportunity-service", fallbackMethod = "getDoubleFallbackMethod")
  public Double getMeanOpportunitiesPerAccount() {
    return opportunityClient.getMeanOpportunitiesPerAccount();
  }

  //#2: The median number of Opportunities associated with an Account
  @GetMapping("/opportunities/median-by-account")
  @Retry(name = "opportunity-service", fallbackMethod = "getDoubleFallbackMethod")
  public Double getMedianOpportunitiesPerAccount() {
    return opportunityClient.getMedianOpportunitiesPerAccount();
  }

  //#3: The maximum number of Opportunities associated with an Account
  @GetMapping("/opportunities/max-by-account")
  @Retry(name = "opportunity-service", fallbackMethod = "getDoubleFallbackMethod")
  public Double getMaxOpportunitiesPerAccount() {
    return opportunityClient.getMaxOpportunitiesPerAccount();
  }

  //#4: The minimum number of Opportunities associated with an Account
  @GetMapping("/opportunities/min-by-account")
  @Retry(name = "opportunity-service", fallbackMethod = "getDoubleFallbackMethod")
  public Double getMinOpportunitiesPerAccount() {
    return opportunityClient.getMinOpportunitiesPerAccount();
  }

  //EmployeeCount#1: The mean number of employees of all the registered companies
  @GetMapping("employeeCount/mean")
  @Retry(name = "account-service", fallbackMethod = "getDoubleFallbackMethod")
  public Double getMeanEmployeeCount() {
    return accountClient.getMeanEmployeeCount();
  }

  //EmployeeCount#2: The median number of employees of all the registered companies
  @GetMapping("employeeCount/median")
  @Retry(name = "account-service", fallbackMethod = "getDoubleFallbackMethod")
  public Double getMedianEmployeeCount() {
    return accountClient.getMedianEmployeeCount();
  }

  //EmployeeCount#3: The max number of employees among all the registered companies
  @GetMapping("employeeCount/max")
  @Retry(name = "account-service", fallbackMethod = "getIntegerFallbackMethod")
  public Integer getMaxEmployeeCount() {
    return accountClient.getMaxEmployeeCount();
  }

  //EmployeeCount#4: The min number of employees among all the registered companies
  @GetMapping("employeeCount/min")
  @Retry(name = "account-service", fallbackMethod = "getIntegerFallbackMethod")
  public Integer getMinEmployeeCount() {
    return accountClient.getMinEmployeeCount();
  }


  // Fallback Methods

  public List<Object[]> getObjectFallbackMethod(Exception e) {
    logger.info("Fallback method called.");
    var result = new ArrayList<Object[]>();
    result.add(new Object[]{"Object not founded", "Please, try again later."});
    return result;
  }

  public Double getDoubleFallbackMethod(Exception e) {
    logger.info("Fallback method called.");
    return 0.0;
  }

  public Long getLongFallbackMethod(Exception e) {
    logger.info("Fallback method called.");
    return 0L;
  }

  public Integer getIntegerFallbackMethod(Exception e) {
    logger.info("Fallback method called.");
    return 0;
  }

}
