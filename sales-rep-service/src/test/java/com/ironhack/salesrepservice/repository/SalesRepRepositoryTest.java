package com.ironhack.salesrepservice.repository;

import com.ironhack.salesrepservice.SalesRepServiceApplication;
import com.ironhack.salesrepservice.dao.SalesRep;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
class SalesRepRepositoryTest {

    @Autowired
    private SalesRepRepository salesRepRepository;

    @BeforeEach
    void setUp() {
        salesRepRepository.saveAll(List.of(
                new SalesRep("David Lynch"),
                new SalesRep("Martha Stewart")
        ));
    }

    @AfterEach
    void tearDown() { ;
        salesRepRepository.deleteAll();
    }

    @Test
    void findAllSalesreps_shouldWork() {
        var salesRepsCount = salesRepRepository.findAll().size();
        assertEquals(2, salesRepsCount);
    }


}