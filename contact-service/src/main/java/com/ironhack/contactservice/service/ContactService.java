package com.ironhack.contactservice.service;

import com.ironhack.contactservice.dao.Contact;
import com.ironhack.contactservice.dto.ContactDTO;
import com.ironhack.contactservice.proxies.SalesRepProxy;
import com.ironhack.contactservice.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    ContactRepository repository;

    @Autowired
    SalesRepProxy salesRepProxy;

    public List<Contact> getList(){
        return repository.findAll();
    }

    public Contact validateCreate(ContactDTO contactDTO){
        boolean salesRepExists = salesRepProxy.checkSalesRep(contactDTO.getSalesRepId());
        if (salesRepExists) {
            Contact contact = new Contact(contactDTO.getName(), contactDTO.getPhoneNumber(), contactDTO.getEmail(), contactDTO.getCompanyName(), contactDTO.getSalesRepId());
            return repository.save(contact);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no sales rep by that id");
    }

    public Contact findLead(Long id){
        Optional<Contact> lead = repository.findById(id);
        return lead.orElse(null);
    }
}
