package com.ironhack.leadservice.service;

import com.ironhack.leadservice.dao.Lead;
import com.ironhack.leadservice.dto.LeadDTO;
import com.ironhack.leadservice.dto.SalesRepDTO;
import com.ironhack.leadservice.proxies.SalesRepProxy;
import com.ironhack.leadservice.repositories.LeadRepository;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LeadService {

  @Autowired
  LeadRepository repository;

  @Autowired
  SalesRepProxy salesRepProxy;

  public List<Lead> getList() {
    return repository.findAll();
  }

  public Lead validateCreate(LeadDTO leadDTO) {
    boolean salesRepExists = salesRepProxy.checkSalesRep(leadDTO.getSalesRepId());
    if (salesRepExists) {
      Lead lead = new Lead(leadDTO.getName(), leadDTO.getPhoneNumber(), leadDTO.getEmail(), leadDTO.getCompanyName(), leadDTO.getSalesRepId());
      return repository.save(lead);
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no sales rep by that id");
  }

  public Lead findLead(Long id) {
    Optional<Lead> lead = repository.findById(id);
    return lead.orElse(null);
    }

    public ResponseEntity<Void> delete(Long id) {
        Optional<Lead> leadToDelete = repository.findById(id);
        repository.deleteById(leadToDelete.get().getLeadId());
        return new ResponseEntity<Void>(HttpStatus.OK);
  }

  public List<Long[]> getCountLeadBySalesRep() {
    return repository.getCountLeadBySalesRep();
  }
}