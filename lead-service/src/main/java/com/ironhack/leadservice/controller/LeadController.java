package com.ironhack.leadservice.controller;

import com.ironhack.leadservice.dao.Lead;
import com.ironhack.leadservice.dto.LeadDTO;
import com.ironhack.leadservice.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lead")
public class LeadController {

  @Autowired
  LeadService leadService;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Lead findById(@PathVariable Long id) {
    return leadService.findLead(id);
  }

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  public List<Lead> findAll() {
    return leadService.getList();
  }

  @PostMapping("/new")
  @ResponseStatus(HttpStatus.CREATED)
  public Lead create(@RequestBody @Valid LeadDTO leadDTO) {
    return leadService.validateCreate(leadDTO);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLead(@PathVariable Long id) { leadService.delete(id); }
    
  @GetMapping("/count-by-salesrep")
  @ResponseStatus(HttpStatus.OK)
  public List<Long[]> getCountOpportunityBySalesRep() {
    return leadService.getCountLeadBySalesRep();
  }
}