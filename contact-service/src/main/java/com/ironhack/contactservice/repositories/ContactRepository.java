package com.ironhack.contactservice.repositories;

import com.ironhack.contactservice.dao.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
