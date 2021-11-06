package com.ironhack.opportunityservice.controller;

import com.ironhack.opportunityservice.dao.Opportunity;
import com.ironhack.opportunityservice.dto.OpportunityDTO;
import com.ironhack.opportunityservice.enums.Status;
import com.ironhack.opportunityservice.repositories.OpportunityRepository;
import com.ironhack.opportunityservice.services.OpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/opportunities")
public class OpportunityController {

  @Autowired
  OpportunityService opportunityService;

  @Autowired
  OpportunityRepository opportunityRepository;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Opportunity retrieveOpportunity(@PathVariable(name = "id") Long id) {
    return opportunityService.getOpportunity(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Opportunity> retrieveAll() {
    return opportunityService.getAll();
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Opportunity create(@RequestBody @Valid OpportunityDTO opportunityDTO) {
    return opportunityService.newOpportunity(opportunityDTO);
  }

  @PutMapping("/closed-won/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Opportunity closedWon(@PathVariable(name = "id") Long id) {
    return opportunityService.closeWon(id);
  }

  @PutMapping("/closed-lost/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Opportunity closedLost(@PathVariable(name = "id") Long id) {
    return opportunityService.closeLost(id);
  }

  @GetMapping("/count-by-salesrep")
  @ResponseStatus(HttpStatus.OK)
  public List<Long[]> getCountOpportunityBySalesRep() {
    return opportunityRepository.getCountOpportunityBySalesRep();
  }

  @GetMapping("/count-by-salesrep/{status}")
  @ResponseStatus(HttpStatus.OK)
  public List<Long[]> getCountOpportunityBySalesRepWithStatus(@PathVariable Status status) {
    return opportunityRepository.getCountOpportunityBySalesRepWithStatus(status);
  }

  @GetMapping("/count-by-product")
  @ResponseStatus(HttpStatus.OK)
  public List<Object[]> getCountOpportunityByProduct() {
    return opportunityRepository.getCountOpportunityByProduct();
  }

  @GetMapping("/count-by-product/{status}")
  public List<Object[]> getCountOpportunityByProductWithStatus(@PathVariable Status status) {
    return opportunityRepository.getCountOpportunityByProductWithStatus(status);
  }

  @GetMapping("/count-by-account")
  @ResponseStatus(HttpStatus.OK)
  public List<Long[]> getCountOpportunityByAccount() {
    return opportunityRepository.getCountOpportunityByAccount();
  }

  @GetMapping("/count-by-account/{status}")
  @ResponseStatus(HttpStatus.OK)
  public List<Long[]> getCountOpportunityByAccountWithStatus(@PathVariable Status status) {
    return opportunityRepository.getCountOpportunityByAccountWithStatus(status);
  }

  @GetMapping("/mean")
  @ResponseStatus(HttpStatus.OK)
  Double getMeanProductQuantity() {
    return opportunityRepository.getMeanProductQuantity().orElse(0.0);
  }

  @GetMapping("/median")
  @ResponseStatus(HttpStatus.OK)
  Double getMedianProductQuantity() {
    var data = opportunityRepository.getMedianQuantityStep1();
    return calcMedian(data);
  }

  @GetMapping("/max")
  @ResponseStatus(HttpStatus.OK)
  Long getMaxProductQuantity() {
    return opportunityRepository.getMaxProductQuantity().orElse(0L);
  }

  @GetMapping("/min")
  @ResponseStatus(HttpStatus.OK)
  Long getMinProductQuantity() {
    return opportunityRepository.getMinProductQuantity().orElse(0L);
  }

  @GetMapping("/mean-by-account")
  @ResponseStatus(HttpStatus.OK)
  public Double getMeanOpportunitiesPerAccount() {
    return opportunityRepository.getMeanOpportunitiesPerAccount().orElseGet(() -> 0.0);
  }

  @GetMapping("/median-by-account")
  @ResponseStatus(HttpStatus.OK)
  public Double getMedianOpportunitiesPerAccount() {
    var data = opportunityRepository.getCountOpportunityByAccount();
    var counts = data.stream().map((datapoint) -> datapoint[1]).sorted().collect(Collectors.toList());

    return calcMedian(counts);
  }

  private double calcMedian(List<Long> counts) {
    //from https://en.wikipedia.org/wiki/Median
    //If n is odd then Median (M) = value of ((n + 1)/2)th item term.
    //If n is even then Median (M) = value of [((n)/2)th item term + ((n)/2 + 1)th item term ]/2
    double median;
    int middle = (counts.size() / 2);
    if (counts.size() % 2 == 0) {
      var medianA = counts.get(middle);
      var medianB = counts.get(middle - 1);
      median = (double) ((medianA + medianB) / 2);
    } else {
      median = (double) counts.get(middle);
    }
    return median;
  }

  @GetMapping("/max-by-account")
  public Double getMaxOpportunitiesPerAccount() {
    return opportunityRepository.getMaxOpportunitiesPerAccount().orElseGet(() -> 0.0);
  }

  @GetMapping("/min-by-account")
  public Double getMinOpportunitiesPerAccount() {
    return opportunityRepository.getMinOpportunitiesPerAccount().orElseGet(() -> 0.0);
  }
}
