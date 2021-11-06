package com.ironhack.contactservice.controller;

import com.ironhack.contactservice.dao.Contact;
import com.ironhack.contactservice.dto.ContactDTO;
import com.ironhack.contactservice.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/{id}")
    public Contact findById(@PathVariable Long id){
        return contactService.findLead(id);
    }

    @GetMapping("/all")
    public List<Contact> findAll(){
        return contactService.getList();
    }

    @PostMapping("/new")
    public Contact create(@RequestBody @Valid ContactDTO contactDTO){
        return contactService.validateCreate(contactDTO);
    }
}
