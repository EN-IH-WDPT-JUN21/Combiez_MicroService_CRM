package com.ironchack.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpportunityDTO {

    private Long id;

    private String status;

    private String product;

    private Integer quantity;

    private Long decisionMaker;

    private Long account;

    private Long salesRep;

    public OpportunityDTO(String product, Integer quantity, Long decisionMaker, Long account, Long salesRep) {
        this.product = product;
        this.quantity = quantity;
        this.decisionMaker = decisionMaker;
        this.account = account;
        this.salesRep = salesRep;
    }
}
