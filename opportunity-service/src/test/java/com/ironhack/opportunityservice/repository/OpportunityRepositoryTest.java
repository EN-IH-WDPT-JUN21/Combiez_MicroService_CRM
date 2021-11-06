package com.ironhack.opportunityservice.repository;

import com.ironhack.opportunityservice.dao.Opportunity;
import com.ironhack.opportunityservice.enums.Status;
import com.ironhack.opportunityservice.enums.Truck;
import com.ironhack.opportunityservice.repositories.OpportunityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpportunityRepositoryTest {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @BeforeEach
    void setUp() {
        opportunityRepository.deleteAll();
        opportunityRepository.saveAll(List.of(
                new Opportunity(Truck.FLATBED, 10, 1L, 2L, 2L),
                new Opportunity(Truck.BOX, 1150, 2L, 3L, 2L),
                new Opportunity(Truck.HYBRID, 1, 4L, 1L, 3L)
        ));
    }

    @Test
    void findCountOpportunityByRepName_Test() {
        var leadByRep = opportunityRepository.getCountOpportunityBySalesRep();

        assertEquals(1L, leadByRep.get(1)[1]);
        assertEquals(2L,leadByRep.get(0)[1]);
    }

    @Test
    void findCountOppForProduct_Test(){
        var oppByProd = opportunityRepository.getCountOpportunityByProduct();
        assertEquals(Truck.BOX, oppByProd.get(0)[0]);
        assertEquals(Truck.FLATBED, oppByProd.get(1)[0]);
        assertEquals(Truck.HYBRID, oppByProd.get(2)[0]);
    }

   @Test
    void findCountOpportunityByRepNameForStatus_TestOpen(){
        var oppByRepOpen = opportunityRepository.getCountOpportunityBySalesRepWithStatus(Status.OPEN);
        assertEquals(1L, oppByRepOpen.get(1)[1]);
        assertEquals(2L,oppByRepOpen.get(0)[1]);
    }

     @Test
    void findCountOpportunityByRepNameForStatus_TestCloseWon(){
        Opportunity opportunity = opportunityRepository.findAll().get(0);
        opportunity.setStatus(Status.CLOSED_WON);
        opportunityRepository.save(opportunity);
        var oppByRepCloseWon = opportunityRepository.getCountOpportunityBySalesRepWithStatus(Status.CLOSED_WON);
        assertEquals(1L,oppByRepCloseWon.get(0)[1]);
    }

   @Test
    void findCountOpportunityByRepNameForStatus_TestCloseLost(){
        Opportunity opportunity = opportunityRepository.findAll().get(0);
        opportunity.setStatus(Status.CLOSED_LOST);
        opportunityRepository.save(opportunity);
        var oppByRepCloseLost = opportunityRepository.getCountOpportunityBySalesRepWithStatus(Status.CLOSED_LOST);
        assertEquals(1L,oppByRepCloseLost.get(0)[1]);
    }

    @Test
    void findCountOpportunityByProductForStatus_OPEN(){
        var oppByProdOpen = opportunityRepository.getCountOpportunityByProductWithStatus(Status.OPEN);
        assertEquals(Truck.BOX, oppByProdOpen.get(0)[0]);
        assertEquals(1L, oppByProdOpen.get(0)[1]);
        assertEquals(Truck.FLATBED, oppByProdOpen.get(1)[0]);
        assertEquals(1L, oppByProdOpen.get(1)[1]);
        assertEquals(Truck.HYBRID, oppByProdOpen.get(2)[0]);
        assertEquals(1L, oppByProdOpen.get(2)[1]);
    }

    @Test
    void findCountOpportunityByProductForStatus_CLOSED_WON(){
        Opportunity opportunity = opportunityRepository.findAll().get(0);
        opportunity.setStatus(Status.CLOSED_WON);
        opportunityRepository.save(opportunity);
        var oppByProdCloseWon = opportunityRepository.getCountOpportunityByProductWithStatus(Status.CLOSED_WON);
        assertEquals(Truck.FLATBED, oppByProdCloseWon.get(0)[0]);
        assertEquals(1L, oppByProdCloseWon.get(0)[1]);

    }

    @Test
    void findCountOpportunityByProductForStatus_CLOSED_LOST(){
        Opportunity opportunity = opportunityRepository.findAll().get(0);
        opportunity.setStatus(Status.CLOSED_LOST);
        opportunityRepository.save(opportunity);
        var oppByProdCloseLost = opportunityRepository.getCountOpportunityByProductWithStatus(Status.CLOSED_LOST);
        assertEquals(Truck.FLATBED, oppByProdCloseLost.get(0)[0]);
        assertEquals(1L, oppByProdCloseLost.get(0)[1]);

    }

    @Test
    void findMeanProductQuantity() {
        var meanProductQuantity = opportunityRepository.getMeanProductQuantity();
        assertEquals(387, meanProductQuantity.get().doubleValue());
    }

    @Test
    void findMedianQuantityStep1_test(){
        var medianQuantity = opportunityRepository.getMedianQuantityStep1();
        assertEquals(3, medianQuantity.toArray().length);
    }

    @Test
    void findMaxProductQuantity() {
        var maxProductQuantity = opportunityRepository.getMaxProductQuantity();
        assertEquals(1150, maxProductQuantity.get().intValue());
    }

    @Test
    void findMinProductQuantity() {
        var minProductQuantity = opportunityRepository.getMinProductQuantity();
        assertEquals(1L, minProductQuantity.get().intValue());
    }

}