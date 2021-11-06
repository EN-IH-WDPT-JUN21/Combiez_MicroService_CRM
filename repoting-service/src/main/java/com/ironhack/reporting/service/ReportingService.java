package com.ironhack.reporting.service;

import com.ironhack.reporting.clients.AccountClient;
import com.ironhack.reporting.clients.LeadClient;
import com.ironhack.reporting.clients.OpportunityClient;
import com.ironhack.reporting.clients.SalesRepClient;
import com.ironhack.reporting.enums.Status;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReportingService {

    private final AccountClient accountClient;
    private final OpportunityClient opportunityClient;
    private final LeadClient leadClient;
    private final SalesRepClient salesRepClient;

    public ReportingService(AccountClient accountClient, OpportunityClient opportunityClient, LeadClient leadClient, SalesRepClient salesRepClient) {
        this.accountClient = accountClient;
        this.opportunityClient = opportunityClient;
        this.leadClient = leadClient;
        this.salesRepClient = salesRepClient;
    }

    public List<Object[]> getCountLeadBySalesRep() {
        var data = leadClient.getCountLeadBySalesRep();
        var result = new ArrayList<Object[]>(data.size());
        for (var kv : data) {
            var salesRep = salesRepClient.getSalesRep(kv[0]);
            result.add(new Object[]{salesRep.getName(), kv[1]});
        }
        return result;
    }

    public List<Object[]> getCountOpportunityBySalesRep() {
        var data = opportunityClient.getCountOpportunityBySalesRep();
        var result = new ArrayList<Object[]>(data.size());
        for (var kv : data) {
            var salesRep = salesRepClient.getSalesRep(kv[0]);
            result.add(new Object[]{salesRep.getName(), kv[1]});
        }
        return result;
    }

    public List<Object[]> getCountOpportunityBySalesRepWithStatus(Status status) {
        var data = opportunityClient.getCountOpportunityBySalesRepWithStatus(status);
        var result = new ArrayList<Object[]>(data.size());
        for (var kv : data) {
            var salesRep = salesRepClient.getSalesRep(kv[0]);
            result.add(new Object[]{salesRep.getName(), kv[1]});
        }
        return result;
    }

    public List<Object[]> getCountOpportunityByProduct() {
        return opportunityClient.getCountOpportunityByProduct();
    }

    public List<Object[]> getCountOpportunityByProductWithStatus(Status status) {
        return opportunityClient.getCountOpportunityByProductWithStatus(status);
    }

    private List<Object[]> computeCountByCountry(List<Long[]> data) {
        var counts = new HashMap<String, Long>();
        for (var kv : data) {
            var account = accountClient.getById(kv[0]);
            counts.put(account.getCountry(), counts.getOrDefault(account.getCountry(), 0L) + kv[1]);
        }

        var result = new ArrayList<Object[]>(counts.size());
        for (var kv : counts.entrySet()) {
            result.add(new Object[]{kv.getKey(), kv.getValue()});
        }

        return result;
    }

    public List<Object[]> getCountOpportunityByCountry() {
        var data = opportunityClient.getCountOpportunityByAccount();
        return computeCountByCountry(data);
    }

    public List<Object[]> getCountOpportunityByCountryWithStatus(Status status) {
        var data = opportunityClient.getCountOpportunityByAccountWithStatus(status);
        return computeCountByCountry(data);
    }

    private List<Object[]> computeCountByCity(List<Long[]> data) {
        var counts = new HashMap<String, Long>();
        for (var kv : data) {
            var account = accountClient.getById(kv[0]);
            counts.put(account.getCity(), counts.getOrDefault(account.getCity(), 0L) + kv[1]);
        }

        var result = new ArrayList<Object[]>(counts.size());
        for (var kv : counts.entrySet()) {
            result.add(new Object[]{kv.getKey(), kv.getValue()});
        }

        return result;
    }

    public List<Object[]> getCountOpportunityByCity() {
        var data = opportunityClient.getCountOpportunityByAccount();
        return computeCountByCity(data);
    }

    public List<Object[]> getCountOpportunityByCityWithStatus(Status status) {
        var data = opportunityClient.getCountOpportunityByAccountWithStatus(status);
        return computeCountByCity(data);
    }

    private List<Object[]> computeCountByIndustry(List<Long[]> data) {
        var counts = new HashMap<String, Long>();
        for (var kv : data) {
            var account = accountClient.getById(kv[0]);
            counts.put(account.getIndustry(), counts.getOrDefault(account.getIndustry(), 0L) + kv[1]);
        }

        var result = new ArrayList<Object[]>(counts.size());
        for (var kv : counts.entrySet()) {
            result.add(new Object[]{kv.getKey(), kv.getValue()});
        }

        return result;
    }

    public List<Object[]> getCountOpportunityByIndustry() {
        var data = opportunityClient.getCountOpportunityByAccount();
        return computeCountByIndustry(data);
    }

    public List<Object[]> getCountOpportunityByIndustryWithStatus(Status status) {
        var data = opportunityClient.getCountOpportunityByAccountWithStatus(status);
        return computeCountByIndustry(data);
    }

}
