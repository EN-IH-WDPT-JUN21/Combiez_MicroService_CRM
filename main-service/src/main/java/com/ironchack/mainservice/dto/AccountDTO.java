package com.ironchack.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private Long id;
    private String industry;
    private Integer employeeCount;
    private String city;
    private String country;
    private List<Long> contactList;
    private List<Long> opportunityList;

    public AccountDTO(String industry, Integer employeeCount, String city, String country, List<Long> contactList, List<Long> opportunityList) {
        this.industry = industry;
        this.employeeCount = employeeCount;
        this.city = city;
        this.country = country;
        this.contactList = contactList;
        this.opportunityList = opportunityList;
    }
}
