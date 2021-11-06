package com.ironhack.opportunityservice.dao;

import com.ironhack.opportunityservice.enums.Status;
import com.ironhack.opportunityservice.enums.Truck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="opportunity")
public class Opportunity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN;

    @Enumerated(EnumType.STRING)
    private Truck product;

    private Integer quantity;

    private Long decisionMaker;

    private Long account; //does this need to be here anymore? Since it'll be on the Account

    private Long salesRep;

    public Opportunity(Truck product, Integer quantity, Long decisionMaker, Long account, Long salesRep) {
        setProduct(product);
        setQuantity(quantity);
        setDecisionMaker(decisionMaker);
        setAccount(account);
        setSalesRep(salesRep);
    }
}
