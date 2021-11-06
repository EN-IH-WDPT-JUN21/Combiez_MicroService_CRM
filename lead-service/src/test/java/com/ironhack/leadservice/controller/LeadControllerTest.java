package com.ironhack.leadservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.leadservice.dao.Lead;
import com.ironhack.leadservice.repositories.LeadRepository;
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
class LeadControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LeadRepository leadRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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
    void findById() throws Exception {
        MvcResult result1 = mockMvc.perform(get("/lead/"
                + leadRepository.findByName("Gustavo Maldonado").getLeadId()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result1.getResponse().getContentAsString().contains("Gustavo Maldonado"));

        MvcResult result2 = mockMvc.perform(get("/lead/"
                + leadRepository.findByName("Varvara Nechaeva").getLeadId()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result2.getResponse().getContentAsString().contains("Varvara Nechaeva"));
    }

    @Test
    void findAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/lead/all"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Gustavo Maldonado"));
        assertTrue(result.getResponse().getContentAsString().contains("Lee Dawson"));
        assertTrue(result.getResponse().getContentAsString().contains("Varvara Nechaeva"));
    }

    @Test
    void create() throws Exception{
        Lead lead = new Lead("Maravillas Fdez", "666777666", "marwey@hotmail.com", "Irish Laura", 1L);

        String body = objectMapper.writeValueAsString(lead);

        int dbLengthBefore = leadRepository.findAll().size();

        MvcResult result = mockMvc.perform(post("/lead/new")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        int dbLengthAfter = leadRepository.findAll().size();

        assertTrue(result.getResponse().getContentAsString().contains("Maravillas Fdez"));
        assertEquals(dbLengthBefore + 1, dbLengthAfter);
    }

    @Test
    void deleteLead() throws Exception {
        int dbLengthBefore = leadRepository.findAll().size();

        mockMvc.perform(delete("/lead/"
                + leadRepository.findByName("Lee Dawson").getLeadId()))
                .andExpect(status().isNoContent())
                .andReturn();

        int dbLengthAfter = leadRepository.findAll().size();

        assertEquals(dbLengthBefore - 1, dbLengthAfter);
    }

}