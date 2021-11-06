package com.ironhack.salesrepservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.salesrepservice.dao.SalesRep;
import com.ironhack.salesrepservice.repository.SalesRepRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SalesRepControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SalesRepRepository salesRepRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        salesRepRepository.save(new SalesRep("David Lynch"));
        salesRepRepository.save(new SalesRep("Martha Stewart"));
    }

    @AfterEach
    void tearDown() {
        salesRepRepository.deleteAll();
    }

    @Test
    void getAllSalesRep() throws Exception {
        MvcResult result = mockMvc.perform(get("/salesRep"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("David Lynch"));
        assertTrue(result.getResponse().getContentAsString().contains("Martha Stewart"));
    }

    @Test
    void getSalesRep() throws Exception {
        MvcResult result1 = mockMvc.perform(get("/salesRep/"
                + salesRepRepository.getByName("David Lynch").getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result1.getResponse().getContentAsString().contains("David Lynch"));

        MvcResult result2 = mockMvc.perform(get("/salesRep/"
                + salesRepRepository.getByName("Martha Stewart").getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result2.getResponse().getContentAsString().contains("Martha Stewart"));
    }

    @Test
    void checkSalesRep() throws Exception {
        MvcResult result = mockMvc.perform(get("/salesRep/check/"
                + salesRepRepository.getByName("David Lynch").getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("true"));

        MvcResult result2 = mockMvc.perform(get("/salesRep/check/10000"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result2.getResponse().getContentAsString().contains("false"));

    }

    @Test
    void saveNewSalesRep() throws Exception{
        SalesRep salesRep = new SalesRep("Gertrud Smithson");

        String body = objectMapper.writeValueAsString(salesRep);

        int dbLengthBefore = salesRepRepository.findAll().size();

        MvcResult result = mockMvc.perform(post("/salesRep")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        int dbLengthAfter = salesRepRepository.findAll().size();

        assertTrue(result.getResponse().getContentAsString().contains("Gertrud"));
        assertEquals(dbLengthBefore + 1, dbLengthAfter);
    }

    @Test
    void deleteSalesRep() throws Exception {
        int dbLengthBefore = salesRepRepository.findAll().size();

        mockMvc.perform(delete("/salesRep/"
                + salesRepRepository.getByName("David Lynch").getId()))
                .andExpect(status().isNoContent())
                .andReturn();

        int dbLengthAfter = salesRepRepository.findAll().size();

        assertEquals(dbLengthBefore - 1, dbLengthAfter);
    }

}