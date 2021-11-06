package com.ironhack.leadservice.repositories;

import com.ironhack.leadservice.dao.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {
  @Query("SELECT salesRepId, COUNT(1) FROM Lead GROUP BY salesRepId ORDER BY salesRepId")
  List<Long[]> getCountLeadBySalesRep();

  Lead findByName(String name);
}
