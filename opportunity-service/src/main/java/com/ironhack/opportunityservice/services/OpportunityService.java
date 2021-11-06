package com.ironhack.opportunityservice.services;

import com.ironhack.opportunityservice.dao.Opportunity;
import com.ironhack.opportunityservice.dto.OpportunityDTO;
import com.ironhack.opportunityservice.enums.Status;
import com.ironhack.opportunityservice.enums.Truck;
import com.ironhack.opportunityservice.repositories.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class OpportunityService {

  @Autowired
  OpportunityRepository opportunityRepository;


  public Opportunity getOpportunity(Long id) {
    Optional<Opportunity> opportunity = opportunityRepository.findById(id);

    if (opportunity.isPresent()) {
      return opportunity.get();
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No opportunity matching that Id");
    }

  }

  public List<Opportunity> getAll() {
    return opportunityRepository.findAll();
  }

  public Opportunity newOpportunity(OpportunityDTO opportunityDTO) {

    if (opportunityDTO.getProduct().toUpperCase(Locale.ROOT).equals("BOX") || opportunityDTO.getProduct().toUpperCase(Locale.ROOT).equals("HYBRID") || opportunityDTO.getProduct().toUpperCase(Locale.ROOT).equals("FLATBED")) {
      Opportunity opportunity = new Opportunity(Truck.valueOf(opportunityDTO.getProduct().toUpperCase(Locale.ROOT)), opportunityDTO.getQuantity(), opportunityDTO.getDecisionMaker(), opportunityDTO.getAccount(), opportunityDTO.getSalesRep());
      return opportunityRepository.save(opportunity);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "product must be either BOX, HYBRID, or FLATBED");
    }


  }

  public Opportunity closeWon(Long id) {
    Optional<Opportunity> opportunity = opportunityRepository.findById(id);

    if (opportunity.isPresent()) {
      opportunity.get().setStatus(Status.CLOSED_WON);
      return opportunityRepository.save(opportunity.get());
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No opportunity matching that Id");
    }
  }

  public Opportunity closeLost(Long id) {
    Optional<Opportunity> opportunity = opportunityRepository.findById(id);

    if (opportunity.isPresent()) {
      opportunity.get().setStatus(Status.CLOSED_LOST);
      return opportunityRepository.save(opportunity.get());
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No opportunity matching that Id");
    }
  }

}
