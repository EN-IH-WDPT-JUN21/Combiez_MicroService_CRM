package com.ironhack.leadservice.repository;

import com.ironhack.leadservice.dao.Lead;
import com.ironhack.leadservice.repositories.LeadRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeadRepositoryTest {

    @Autowired
    private LeadRepository leadRepository;


    @BeforeEach
    void setUp() {
        leadRepository.saveAll(List.of(
                new Lead("Gustavo Maldonado", "123456789", "gustavomaldonado@gmail.co", "Wings of Freedom", 2L),
                new Lead("Lee Dawson", "980651164", "ld@gmail.com", "LeeD", 3L),
                new Lead("Varvara Nechaeva", "563782789", "varvara-nechaeva@yahoo.com", "Varvara Speed", 2L)
        ));


    }

    @AfterEach
    void tearDown() {
       leadRepository.deleteAll();
    }


    @Test
    public void createLeads_positiveCreation(){
        int repositorySizeBefore = leadRepository.findAll().size();

        leadRepository.save(new Lead("Maravillas Fdez", "666777666", "marwey@hotmail.com", "Irish Laura", 1L));

        int repositorySizeAfter = leadRepository.findAll().size();

        assertEquals(repositorySizeBefore + 1, repositorySizeAfter);
    }


    @Test
    void getCountOfLeadsGroupBySalesRep_Test() {
        var leadByRep = leadRepository.getCountLeadBySalesRep();
        assertEquals(2, leadByRep.get(0)[0]);
    }


}