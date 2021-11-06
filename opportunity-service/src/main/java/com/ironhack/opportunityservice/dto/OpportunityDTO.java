package com.ironhack.opportunityservice.dto;


import com.ironhack.opportunityservice.enums.Status;
import com.ironhack.opportunityservice.enums.Truck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpportunityDTO {


    //@NotBlank(message =  "A Product must be supplied")
    @NotBlank(message = "a product must be provided")
    private String product;

    @NotNull(message = "A quantity must be supplied")
    private Integer quantity;

    @NotNull(message = "A decision maker id must be supplied")
    private Long decisionMaker;

    @NotNull(message = "An account id must be supplied")
    private Long account;

    @NotNull(message = "A sales rep id must be supplied")
    private Long salesRep;

}
