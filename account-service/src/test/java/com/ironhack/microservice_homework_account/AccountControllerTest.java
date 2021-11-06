package com.ironhack.microservice_homework_account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.microservice_homework_account.dao.Account;
import com.ironhack.microservice_homework_account.dao.enums.Industry;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountControllerTest {

  private MockMvc mockMvc;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setup(@Autowired WebApplicationContext webApplicationContext,
             @Autowired AccountRepository accountRepository
  ) {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    accountRepository.save(new Account(1L, Industry.ECOMMERCE, 10, "Warsaw", "USA", null, null));
    accountRepository.save(new Account(2L, Industry.ECOMMERCE, 1000, "Berlin", "USA", List.of(1L), List.of(1L)));
    accountRepository.save(new Account(3L, Industry.MANUFACTURING, 100, "London", "UK", null, null));
  }

  @SneakyThrows
  @Test
  void getAll() {
    MvcResult mvcResult =
        mockMvc
            .perform(get("/account"))
            .andExpect(status().isOk())
            .andReturn();
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Warsaw"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Berlin"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("London"));
  }

  @SneakyThrows
  @Test
  void getById() {
    MvcResult mvcResult =
        mockMvc
            .perform(get("/account/1"))
            .andExpect(status().isOk())
            .andReturn();
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Warsaw"));
  }

  @SneakyThrows
  @Test
  void getByNonExistentIdReturnsNotFound() {
    MvcResult mvcResult =
        mockMvc
            .perform(get("/account/999"))
            .andExpect(status().isNotFound())
            .andReturn();
  }

  @SneakyThrows
  @Test
  void deleteById() {
    MvcResult mvcResult =
        mockMvc
            .perform(delete("/account/1"))
            .andExpect(status().isOk())
            .andReturn();
    //assertTrue(mvcResult.getResponse().getContentAsString().contains("Warsaw"));
  }

  @SneakyThrows
  @Test
  void deleteNonExistentIdReturnsNotFound() {
    MvcResult mvcResult =
        mockMvc
            .perform(delete("/account/999"))
            .andExpect(status().isNotFound())
            .andReturn();
  }

  @SneakyThrows
  @Test
  void createNewAccount() {
    String body = objectMapper.writeValueAsString(new Account(0L, Industry.ECOMMERCE, 10, "Vienna", "AUSTRIA", null, null));
    MvcResult mvcResult =
        mockMvc
            .perform(post("/account").content(body).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    assertTrue(mvcResult.getResponse().getContentAsString().contains("Vienna"));
  }

  @SneakyThrows
  @Test
  void createNewAccountWithNegativeEmployeeCountFails() {
    String body = objectMapper.writeValueAsString(new Account(0L, Industry.ECOMMERCE, -10, "Vienna", "AUSTRIA", null, null));
    MvcResult mvcResult =
        mockMvc
            .perform(post("/account").content(body).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    assertTrue(mvcResult.getResolvedException().getMessage().contains(("Employee count must be positive")));
  }


  @SneakyThrows
  @Test
  void createNewAccountWithWrongCityNameFails() {
    String body = objectMapper.writeValueAsString(new Account(0L, Industry.ECOMMERCE, 10, "123Vienna", "AUSTRIA", null, null));
    MvcResult mvcResult =
        mockMvc
            .perform(post("/account").content(body).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    assertTrue(mvcResult.getResolvedException().getMessage().contains(("City can not contain numbers")));
  }

  @SneakyThrows
  @Test
  void createNewAccountWithEmptyCityNameFails() {
    String body = objectMapper.writeValueAsString(new Account(0L, Industry.ECOMMERCE, 10, "", "AUSTRIA", null, null));
    MvcResult mvcResult =
        mockMvc
            .perform(post("/account").content(body).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    assertTrue(mvcResult.getResolvedException().getMessage().contains(("No city input")));
  }

  @SneakyThrows
  @Test
  void createNewAccountWithTooLongCityNameFails() {
    String body = objectMapper.writeValueAsString(new Account(0L, Industry.ECOMMERCE, 10, "abcdefgabcdefgabcdefgabcdefg", "AUSTRIA", null, null));
    MvcResult mvcResult =
        mockMvc
            .perform(post("/account").content(body).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    assertTrue(mvcResult.getResolvedException().getMessage().contains(("City name exceeds maximum length of 25 characters")));
  }


  @SneakyThrows
  @Test
  void createNewAccountWithWrongCountryNameFails() {
    String body = objectMapper.writeValueAsString(new Account(0L, Industry.ECOMMERCE, 10, "Vienna", "USA", null, null));
    MvcResult mvcResult =
        mockMvc
            .perform(post("/account").content(body).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    assertTrue(mvcResult.getResolvedException().getMessage().contains(("That is not a real country")));
  }

  @SneakyThrows
  @Test
  void createNewAccountWithEmptyCountryNameFails() {
    String body = objectMapper.writeValueAsString(new Account(0L, Industry.ECOMMERCE, 10, "Vienna", "", null, null));
    MvcResult mvcResult =
        mockMvc
            .perform(post("/account").content(body).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    assertTrue(mvcResult.getResolvedException().getMessage().contains(("No country input")));
  }

  @SneakyThrows
  @Test
  void createNewAccountWithTooLongCountryNameFails() {
    String body = objectMapper.writeValueAsString(new Account(0L, Industry.ECOMMERCE, 10, "Vienna", String.format("%1$26s", "x"), null, null));
    MvcResult mvcResult =
        mockMvc
            .perform(post("/account").content(body).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    assertTrue(mvcResult.getResolvedException().getMessage().contains(("Country name exceeds maximum length of 25 characters")));
  }

  @SneakyThrows
  @Test
  void addOpportunity(
//      @Autowired AccountRepository accountRepository
  ) {
    MvcResult mvcResult =
        mockMvc
            .perform(put("/account/1/opportunity").content("1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

//    var actual = accountRepository.findById(1L).get().getOpportunityList();
//    List<Long> expected = List.of(1L);
//    assertTrue(expected.containsAll(actual));
//    assertTrue(actual.containsAll(expected));
  }

  @SneakyThrows
  @Test
  void addOpportunityWrongAccountIdFails() {
    MvcResult mvcResult =
        mockMvc
            .perform(put("/account/999/opportunity").content("1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();
  }

  @SneakyThrows
  @Test
  void addContact() {
    mockMvc
        .perform(put("/account/1/contact").content("1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
  }

  @SneakyThrows
  @Test
  void addContactWrongAccountIdFails() {
    mockMvc
        .perform(put("/account/9999/contact").content("1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn();
  }

  @SneakyThrows
  @Test
  void removeOpportunity() {
    MvcResult mvcResult =
        mockMvc
            .perform(delete("/account/2/opportunity").param("id", "1"))
            .andExpect(status().isOk())
            .andReturn();
  }

  @SneakyThrows
  @Test
  void removeOpportunityWrongAccountIdFails() {
    MvcResult mvcResult =
        mockMvc
            .perform(delete("/account/29999/opportunity").param("id", "1"))
            .andExpect(status().isNotFound())
            .andReturn();
  }

  @SneakyThrows
  @Test
  void removeContact() {
    MvcResult mvcResult =
        mockMvc
            .perform(delete("/account/2/contact").param("id", "1"))
            .andExpect(status().isOk())
            .andReturn();
  }

  @SneakyThrows
  @Test
  void removeContactWrongAccountIdFails() {
    MvcResult mvcResult =
        mockMvc
            .perform(delete("/account/299999/contact").param("id", "1"))
            .andExpect(status().isNotFound())
            .andReturn();
  }
}