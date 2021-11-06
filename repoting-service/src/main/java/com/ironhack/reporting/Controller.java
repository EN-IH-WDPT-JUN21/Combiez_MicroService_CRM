package com.ironhack.reporting;

import com.ironhack.reporting.clients.AccountClient;
import com.ironhack.reporting.clients.LeadClient;
import com.ironhack.reporting.clients.OpportunityClient;
import com.ironhack.reporting.clients.SalesRepClient;
import com.ironhack.reporting.enums.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/report")
public class Controller {
  private final AccountClient accountClient;
  private final OpportunityClient opportunityClient;
  private final LeadClient leadClient;
  private final SalesRepClient salesRepClient;

  public Controller(AccountClient accountClient, OpportunityClient opportunityClient, LeadClient leadClient, SalesRepClient salesRepClient) {
    this.accountClient = accountClient;
    this.opportunityClient = opportunityClient;
    this.leadClient = leadClient;
    this.salesRepClient = salesRepClient;
  }

  //BySalesRep#1: A count of Leads by SalesRep
  @GetMapping("/leads/count-by-salesrep")
  public List<Object[]> getCountLeadBySalesRep() {
    var data = leadClient.getCountLeadBySalesRep();
    var result = new ArrayList<Object[]>(data.size());
    for (var kv : data) {
      var salesRep = salesRepClient.getSalesRep(kv[0]);
      result.add(new Object[]{salesRep.getName(), kv[1]});
    }
    return result;
  }

  //BySalesRep#2: A count of all Opportunities by SalesRep
  @GetMapping("/opportunities/count-by-salesrep")
  public List<Object[]> getCountOpportunityBySalesRep() {
    var data = opportunityClient.getCountOpportunityBySalesRep();
    var result = new ArrayList<Object[]>(data.size());
    for (var kv : data) {
      var salesRep = salesRepClient.getSalesRep(kv[0]);
      result.add(new Object[]{salesRep.getName(), kv[1]});
    }
    return result;
  }

  //BySalesRep#3: A count of all CLOSED_WON Opportunities by SalesRep
  //BySalesRep#4: A count of all CLOSED_LOST Opportunities by SalesRep
  //BySalesRep#5: A count of all OPEN Opportunities by SalesRep
  @GetMapping("/opportunities/count-by-salesrep/{status}")
  public List<Object[]> getCountOpportunityBySalesRepWithStatus(@PathVariable Status status) {
    var data = opportunityClient.getCountOpportunityBySalesRepWithStatus(status);
    var result = new ArrayList<Object[]>(data.size());
    for (var kv : data) {
      var salesRep = salesRepClient.getSalesRep(kv[0]);
      result.add(new Object[]{salesRep.getName(), kv[1]});
    }
    return result;
  }

  //ByProduct#1: A count of all Opportunities by the product
  //ByProduct#2: A count of all CLOSED_WON Opportunities by the product
  //ByProduct#3: A count of all CLOSED_LOST Opportunities by the product
  //ByProduct#4: A count of all OPEN Opportunities by the product
  @GetMapping("/opportunities/count-by-product")
  public List<Object[]> getCountOpportunityByProduct() {
    return opportunityClient.getCountOpportunityByProduct();
  }

  @GetMapping("/opportunities/count-by-product/{status}")
  public List<Object[]> getCountOpportunityByProductWithStatus(@PathVariable Status status) {
    return opportunityClient.getCountOpportunityByProductWithStatus(status);
  }

  //ByCountry#1: A count of all Opportunities by country
  //ByCountry#2: A count of all CLOSED_WON Opportunities by country
  //ByCountry#3: A count of all CLOSED_LOST Opportunities by country
  //ByCountry#4: A count of all OPEN Opportunities by country
  @GetMapping("/opportunities/count-by-country/")
  public List<Object[]> getCountOpportunityByCountry() {
    var data = opportunityClient.getCountOpportunityByAccount();
    return computeCountByCountry(data);
  }

  @GetMapping("/opportunities/count-by-country/{status}")
  public List<Object[]> getCountOpportunityByCountryWithStatus(@PathVariable Status status) {
    var data = opportunityClient.getCountOpportunityByAccountWithStatus(status);
    return computeCountByCountry(data);
  }

  private List<Object[]> computeCountByCountry(List<Long[]> data) {
    var counts = new HashMap<String, Long>();
    for (var kv : data) {
      var account = accountClient.getById(kv[0]);
      counts.put(account.getCountry(), counts.getOrDefault(account.getCountry(), 0L) + kv[1]);
    }

    var result = new ArrayList<Object[]>(counts.size());
    for (var kv : counts.entrySet()) {
      result.add(new Object[]{kv.getKey(), kv.getValue()});
    }

    return result;
  }

  //ByCity#1: A count of all Opportunities by the city
  //ByCity#2: A count of all CLOSED_WON Opportunities by the city
  //ByCity#3: A count of all CLOSED_LOST Opportunities by the city
  //ByCity#4: A count of all OPEN Opportunities by the city
  @GetMapping("/opportunities/count-by-city/")
  public List<Object[]> getCountOpportunityByCity() {
    var data = opportunityClient.getCountOpportunityByAccount();
    return computeCountByCity(data);
  }

  @GetMapping("/opportunities/count-by-city/{status}")
  public List<Object[]> getCountOpportunityByCityWithStatus(@PathVariable Status status) {
    var data = opportunityClient.getCountOpportunityByAccountWithStatus(status);
    return computeCountByCity(data);
  }

  private List<Object[]> computeCountByCity(List<Long[]> data) {
    var counts = new HashMap<String, Long>();
    for (var kv : data) {
      var account = accountClient.getById(kv[0]);
      counts.put(account.getCity(), counts.getOrDefault(account.getCity(), 0L) + kv[1]);
    }

    var result = new ArrayList<Object[]>(counts.size());
    for (var kv : counts.entrySet()) {
      result.add(new Object[]{kv.getKey(), kv.getValue()});
    }

    return result;
  }

  //ByIndustry#1: A count of all Opportunities by industry
  //ByIndustry#2: A count of all CLOSED_WON Opportunities by industry
  //ByIndustry#3: A count of all CLOSED_LOST Opportunities by industry
  //ByIndustry#4: A count of all OPEN Opportunities by industry
  @GetMapping("/opportunities/count-by-industry/")
  public List<Object[]> getCountOpportunityByIndustry() {
    var data = opportunityClient.getCountOpportunityByAccount();
    return computeCountByIndustry(data);
  }

  @GetMapping("/opportunities/count-by-industry/{status}")
  public List<Object[]> getCountOpportunityByIndustryWithStatus(@PathVariable Status status) {
    var data = opportunityClient.getCountOpportunityByAccountWithStatus(status);
    return computeCountByIndustry(data);
  }

  private List<Object[]> computeCountByIndustry(List<Long[]> data) {
    var counts = new HashMap<String, Long>();
    for (var kv : data) {
      var account = accountClient.getById(kv[0]);
      counts.put(account.getIndustry(), counts.getOrDefault(account.getIndustry(), 0L) + kv[1]);
    }

    var result = new ArrayList<Object[]>(counts.size());
    for (var kv : counts.entrySet()) {
      result.add(new Object[]{kv.getKey(), kv.getValue()});
    }

    return result;
  }

  //Quantity#1: The mean quantity of products order
  @GetMapping("/opportunities/mean")
  Double getMeanProductQuantity() {
    return opportunityClient.getMeanProductQuantity();
  }

  //Quantity#2: The median quantity of products order
  @GetMapping("/opportunities/median")
  Double getMedianProductQuantity() {
    return opportunityClient.getMedianProductQuantity();
  }

  //Quantity#3: The max quantity of products order
  @GetMapping("/opportunities/max")
  Long getMaxProductQuantity() {
    return opportunityClient.getMaxProductQuantity();
  }

  //Quantity#4: The min quantity of products order
  @GetMapping("/opportunities/min")
  Long getMinProductQuantity() {
    return opportunityClient.getMinProductQuantity();
  }

  //#1: The mean number of Opportunities associated with an Account
  @GetMapping("/opportunities/mean-by-account/")
  public Double getMeanOpportunitiesPerAccount() {
    return opportunityClient.getMeanOpportunitiesPerAccount();
  }

  //#2: The median number of Opportunities associated with an Account
  @GetMapping("/opportunities/median-by-account")
  public Double getMedianOpportunitiesPerAccount() {
    return opportunityClient.getMedianOpportunitiesPerAccount();
  }

  //#3: The maximum number of Opportunities associated with an Account
  @GetMapping("/opportunities/max-by-account")
  public Double getMaxOpportunitiesPerAccount() {
    return opportunityClient.getMaxOpportunitiesPerAccount();
  }

  //#4: The minimum number of Opportunities associated with an Account
  @GetMapping("/opportunities/min-by-account")
  public Double getMinOpportunitiesPerAccount() {
    return opportunityClient.getMinOpportunitiesPerAccount();
  }

  //EmployeeCount#1: The mean number of employees of all the registered companies
  @GetMapping("employeeCount/mean")
  public Double getMeanEmployeeCount() {
    return accountClient.getMeanEmployeeCount();
  }

  //EmployeeCount#2: The median number of employees of all the registered companies
  @GetMapping("employeeCount/median")
  public Double getMedianEmployeeCount() {
    return accountClient.getMedianEmployeeCount();
  }

  //EmployeeCount#3: The max number of employees among all the registered companies
  @GetMapping("employeeCount/max")
  public Integer getMaxEmployeeCount() {
    return accountClient.getMaxEmployeeCount();
  }

  //EmployeeCount#4: The min number of employees among all the registered companies
  @GetMapping("employeeCount/min")
  public Integer getMinEmployeeCount() {
    return accountClient.getMinEmployeeCount();
  }

}
