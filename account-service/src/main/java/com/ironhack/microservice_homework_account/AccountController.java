package com.ironhack.microservice_homework_account;

import com.ironhack.microservice_homework_account.dao.Account;
import com.ironhack.microservice_homework_account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  private AccountService accountService;

  @GetMapping()
  public List<Account> getAll() {
    return accountRepository.findAll();
  }

  @GetMapping("/{id}")
  public Account getById(@PathVariable Long id) {
    return accountRepository.findById(id).orElseThrow(
        () ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account " + id + " not found"));
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    var dao = accountRepository.findById(id).orElseThrow(
        () ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account " + id + " not found"));
    accountRepository.delete(dao);
    //return dao;
  }

  @PostMapping
  public Account createNewAccount(@RequestBody Account account) {
    return accountRepository.save(account);
  }

  @PutMapping("/{id}/opportunity")
  public Account addOpportunity(@PathVariable Long id, @RequestBody Long opportunityId) {
    var dao = accountRepository.findById(id).orElseThrow(
        () ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account " + id + " not found"));

    // in case this service should also save the opportunity, do it here.
    dao.getOpportunityList().add(opportunityId);
    return accountRepository.save(dao);
  }

  @PutMapping("/{id}/contact")
  public Account addContact(@PathVariable Long id, @RequestBody Long contactId) {
    var dao = accountRepository.findById(id).orElseThrow(
        () ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account " + id + " not found"));

    // in case this service should also save the contact, do it here.
    dao.getContactList().add(contactId);
    return accountRepository.save(dao);
  }

  @DeleteMapping("/{id}/opportunity")
  public Account removeOpportunity(@PathVariable Long id, @RequestParam(name = "id") Long opportunityId) {
    var dao = accountRepository.findById(id).orElseThrow(
        () ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account " + id + " not found"));

    // in case this service should also save the opportunity, do it here.
    dao.getOpportunityList().removeIf(Predicate.isEqual(opportunityId));
    return accountRepository.save(dao);
  }

  @DeleteMapping("/{id}/contact")
  public Account removeContact(@PathVariable Long id, @RequestParam(name = "id") Long contactId) {
    var dao = accountRepository.findById(id).orElseThrow(
        () ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account " + id + " not found"));

    // in case this service should also save the opportunity, do it here.
    dao.getContactList().removeIf(Predicate.isEqual(contactId));
    return accountRepository.save(dao);
  }

  @GetMapping("/employeeCount/mean")
  @ResponseStatus(HttpStatus.OK)
  public Double getMeanEmployeeCount() {
    return accountRepository.findMeanEmployeeCount().get();
  }

  @GetMapping("/employeeCount/median")
  @ResponseStatus(HttpStatus.OK)
  public Double getMedianEmployeeCount() {
    return accountService.findMedianEmployeeCount();
  }

  @GetMapping("/employeeCount/min")
  @ResponseStatus(HttpStatus.OK)
  public Integer getMinEmployeeCount() {
    return accountRepository.findMinEmployeeCount().get();
  }

  @GetMapping("/employeeCount/max")
  @ResponseStatus(HttpStatus.OK)
  public Integer getMaxEmployeeCount() {
    return accountRepository.findMaxEmployeeCount().get();
  }

  /*
  @SneakyThrows
  @GetMapping("/dummy")
  public Account createDummyAccount() {
    var acc = new Account();
    acc.setCity("Warsaw");
    acc.setCountry("UNITED STATES");
    acc.setEmployeeCount(10);
    acc.setIndustry(Industry.ECOMMERCE);
    acc.setContactList(List.of(1L, 2L, 3L));
    acc.setOpportunityList(List.of(11L, 12L, 13L));
    return accountRepository.save(acc);
  }
  //*/
}
