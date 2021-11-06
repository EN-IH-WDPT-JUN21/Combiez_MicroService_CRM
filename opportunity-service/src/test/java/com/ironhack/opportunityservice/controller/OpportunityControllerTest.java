package com.ironhack.opportunityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.opportunityservice.dao.Opportunity;
import com.ironhack.opportunityservice.enums.Status;
import com.ironhack.opportunityservice.enums.Truck;
import com.ironhack.opportunityservice.repositories.OpportunityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OpportunityControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private OpportunityRepository opportunityRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        opportunityRepository.deleteAll();
        opportunityRepository.saveAll(List.of(
                new Opportunity(Truck.FLATBED, 10, 1L, 2L, 2L),
                new Opportunity(Truck.BOX, 1150, 2L, 3L, 2L),
                new Opportunity(Truck.HYBRID, 1, 4L, 1L, 3L)
        ));
    }

    @Test
    void retrieveOpportunity() throws Exception {
        MvcResult result = mockMvc.perform(get("/opportunities/" + opportunityRepository.findAll().get(0).getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("FLATBED"));
        assertFalse(result.getResponse().getContentAsString().contains("BOX"));
    }

    @Test
    void retrieveAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/opportunities"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("FLATBED"));
        assertTrue(result.getResponse().getContentAsString().contains("BOX"));
        assertTrue(result.getResponse().getContentAsString().contains("HYBRID"));
    }

    @Test
    void create() throws Exception {
        Opportunity opportunity = new Opportunity(Truck.FLATBED, 17, 2L, 3L, 1L);

        String body = objectMapper.writeValueAsString(opportunity);

        int dbLengthBefore = opportunityRepository.findAll().size();

        MvcResult result = mockMvc.perform(post("/opportunities/create")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        int dbLengthAfter = opportunityRepository.findAll().size();

        assertTrue(result.getResponse().getContentAsString().contains("FLATBED"));
        assertEquals(dbLengthBefore + 1, dbLengthAfter);
    }

    @Test
    void closedWon() throws Exception {
        String statusBefore = opportunityRepository.findAll().get(0).getStatus().toString();

        MvcResult result = mockMvc.perform(put("/opportunities/closed-won/" + opportunityRepository.findAll().get(0).getId()))
                .andExpect(status().isAccepted())
                .andReturn();

        assertEquals(statusBefore, "OPEN");
        assertTrue(result.getResponse().getContentAsString().contains("CLOSED_WON"));
    }

    @Test
    void closedLost() throws Exception {
        String statusBefore = opportunityRepository.findAll().get(1).getStatus().toString();

        MvcResult result = mockMvc.perform(put("/opportunities/closed-lost/" + opportunityRepository.findAll().get(0).getId()))
                .andExpect(status().isAccepted())
                .andReturn();

        assertEquals(statusBefore, "OPEN");
        assertTrue(result.getResponse().getContentAsString().contains("CLOSED_LOST"));
    }

    @Test
    void getCountOpportunityBySalesRep() throws Exception {
        MvcResult result = mockMvc.perform(get("/opportunities/count-by-salesrep"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("2,2"));
        assertTrue(result.getResponse().getContentAsString().contains("3,1"));
    }

    @Test
    void getCountOpportunityBySalesRepWithStatus() throws Exception {
        Opportunity opportunity = opportunityRepository.findAll().get(0);
        opportunity.setStatus(Status.CLOSED_WON);
        opportunityRepository.save(opportunity);

        MvcResult result = mockMvc.perform(get("/opportunities/count-by-salesrep/" + Status.CLOSED_WON))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(opportunityRepository.findAll().get(0).getSalesRep() +",1"));
    }

    @Test
    void getCountOpportunityByProduct() throws Exception {
        Opportunity opportunity = opportunityRepository.findAll().get(0);
        opportunity.setProduct(Truck.HYBRID);
        opportunityRepository.save(opportunity);

        MvcResult result = mockMvc.perform(get("/opportunities/count-by-product"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("BOX"));
        assertTrue(result.getResponse().getContentAsString().contains("HYBRID"));
        assertFalse(result.getResponse().getContentAsString().contains("FLATBED"));
    }

    @Test
    void getCountOpportunityByProductWithStatus() throws Exception {
        Opportunity opportunity = opportunityRepository.findAll().get(0);
        opportunity.setStatus(Status.CLOSED_WON);
        opportunityRepository.save(opportunity);


        MvcResult result = mockMvc.perform(get("/opportunities/count-by-product/" + Status.CLOSED_WON))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("FLATBED"));
        assertTrue(result.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getCountOpportunityByAccount() throws Exception {
        MvcResult result = mockMvc.perform(get("/opportunities/count-by-account"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("1,1"));
        assertTrue(result.getResponse().getContentAsString().contains("2,1"));
        assertTrue(result.getResponse().getContentAsString().contains("3,1"));
    }

    @Test
    void getCountOpportunityByAccountWithStatus() throws Exception {
        Opportunity opportunity = opportunityRepository.findAll().get(0);
        opportunity.setStatus(Status.CLOSED_WON);
        opportunityRepository.save(opportunity);


        MvcResult result = mockMvc.perform(get("/opportunities/count-by-account/" + Status.CLOSED_WON))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("2,1"));
    }

    @Test
    void getMeanProductQuantity() throws Exception {
        MvcResult result = mockMvc.perform(get("/opportunities/mean"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("387"));
    }

    @Test
    void getMedianProductQuantity() throws Exception {
        MvcResult result = mockMvc.perform(get("/opportunities/median"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("10"));
    }

    @Test
    void getMaxProductQuantity() throws Exception {
        MvcResult result = mockMvc.perform(get("/opportunities/max"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("1150"));
    }

    @Test
    void getMinProductQuantity() throws Exception {
        MvcResult result = mockMvc.perform(get("/opportunities/min"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("1"));
    }

    void getMinProductQuantity_returnDefault() throws Exception {
        opportunityRepository.deleteAll();

        MvcResult result = mockMvc.perform(get("/opportunities/min"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("0.0"));
    }


    @Test
    void getMeanOpportunitiesPerAccount() throws Exception {
        MvcResult result = mockMvc.perform(get("/opportunities/mean-by-account"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getMedianOpportunitiesPerAccount() throws Exception {
        Opportunity opportunity = new Opportunity(Truck.FLATBED, 1, 2L, 3L, 1L);
        Opportunity opportunity2 = new Opportunity(Truck.BOX, 7, 2L, 3L, 1L);
        Opportunity opportunity3 = new Opportunity(Truck.HYBRID, 80, 2L, 2L, 1L);
        opportunityRepository.saveAll(List.of(opportunity, opportunity2, opportunity3));

        MvcResult result = mockMvc.perform(get("/opportunities/median-by-account"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("2"));
    }

    @Test
    void getMaxOpportunitiesPerAccount() throws Exception {
        Opportunity opportunity = new Opportunity(Truck.FLATBED, 17, 2L, 3L, 1L);
        opportunityRepository.save(opportunity);

        MvcResult result = mockMvc.perform(get("/opportunities/max-by-account"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("2"));
    }

    @Test
    void getMinOpportunitiesPerAccount() throws Exception {
        MvcResult result = mockMvc.perform(get("/opportunities/min-by-account"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("1"));
    }
}