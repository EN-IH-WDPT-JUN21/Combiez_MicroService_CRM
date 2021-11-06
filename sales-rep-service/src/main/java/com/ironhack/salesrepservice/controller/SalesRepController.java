package com.ironhack.salesrepservice.controller;

import com.ironhack.salesrepservice.dao.SalesRep;
import com.ironhack.salesrepservice.repository.SalesRepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/salesRep")
public class SalesRepController {

    @Autowired
    private SalesRepRepository salesRepRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SalesRep> getAllSalesRep() {
        return salesRepRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalesRep getSalesRep(@PathVariable("id") Long id) {
        return salesRepRepository.findById(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalesRep saveNewSalesRep(@RequestBody @Valid SalesRep salesRep) {
        salesRepRepository.save(salesRep);
        return salesRep;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSalesRep(@PathVariable("id") Long id) {
        salesRepRepository.deleteById(id);
    }

    // Proxy to check if salesRep exists
    @GetMapping("/check/{salesRepId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkSalesRep(@PathVariable Long salesRepId){
        return salesRepRepository.existsById(salesRepId);
    };

}
