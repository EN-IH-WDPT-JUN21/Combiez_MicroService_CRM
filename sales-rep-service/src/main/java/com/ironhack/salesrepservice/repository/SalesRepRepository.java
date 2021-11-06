package com.ironhack.salesrepservice.repository;

import com.ironhack.salesrepservice.dao.SalesRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepRepository extends JpaRepository<SalesRep, Long> {

    SalesRep getByName(String name);

}
