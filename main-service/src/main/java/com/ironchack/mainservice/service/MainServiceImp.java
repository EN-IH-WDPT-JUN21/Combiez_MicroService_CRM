package com.ironchack.mainservice.service;

import com.ironchack.mainservice.dto.AccountDTO;
import com.ironchack.mainservice.dto.ConvertLeadDTO;
import com.ironchack.mainservice.dto.DecisionMakerDTO;
import com.ironchack.mainservice.dto.OpportunityDTO;
import com.ironchack.mainservice.proxy.AccountProxy;
import com.ironchack.mainservice.proxy.ContactProxy;
import com.ironchack.mainservice.proxy.LeadProxy;
import com.ironchack.mainservice.proxy.OpportunityProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@Qualifier("main")
public class MainServiceImp implements MainService{

    private final LeadProxy leadProxy;
    private final ContactProxy contactProxy;
    private final OpportunityProxy opportunityProxy;
    private final AccountProxy accountProxy;

    public MainServiceImp(LeadProxy leadProxy, ContactProxy contactProxy, OpportunityProxy opportunityProxy, AccountProxy accountProxy) {
        this.leadProxy = leadProxy;
        this.contactProxy = contactProxy;
        this.opportunityProxy = opportunityProxy;
        this.accountProxy = accountProxy;
    }

    @Override
    public String convertLead(Long id, ConvertLeadDTO convertLeadDTO) {

        DecisionMakerDTO leadToConvert = leadProxy.getLeadInfo(id);
        Long salesRepId = leadToConvert.getSalesRepId();
        DecisionMakerDTO newContact = contactProxy.createContact(leadToConvert);
        leadProxy.delete(id);

        OpportunityDTO newOpp = opportunityProxy.create(
                                    new OpportunityDTO(
                                            convertLeadDTO.getProduct(),
                                            convertLeadDTO.getQuantity(),
                                            newContact.getDecisionMakerId(),
                                            0L,
                                            salesRepId));
        AccountDTO newAccount = accountProxy.create(
                                    new AccountDTO(
                                            convertLeadDTO.getIndustry(),
                                            convertLeadDTO.getQuantity(),
                                            convertLeadDTO.getCity(),
                                            convertLeadDTO.getCountry(),
                                            new ArrayList<Long>(Arrays.asList(newContact.getDecisionMakerId())),
                                            new ArrayList<Long>(Arrays.asList(newOpp.getId()))
                                    )
        );

    return "Lead converted";
    }
}
