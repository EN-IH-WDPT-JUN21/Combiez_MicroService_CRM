package com.ironchack.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DecisionMakerDTO {

    // Works for both lead and contact
    private Long decisionMakerId;

    private String name;

    private String phoneNumber;

    private String email;

    private String companyName;

    private Long salesRepId;
}
