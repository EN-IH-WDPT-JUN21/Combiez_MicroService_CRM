package com.ironhack.reporting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Account {
  private Long id;
  private String industry;
  private Integer employeeCount;
  private String city;
  private String country;
  private List<Long> contactList = new ArrayList<>();
  private List<Long> opportunityList = new ArrayList<>();
}

