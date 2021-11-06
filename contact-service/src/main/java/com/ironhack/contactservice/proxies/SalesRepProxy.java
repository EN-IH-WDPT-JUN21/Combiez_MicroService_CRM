package com.ironhack.contactservice.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("sales-rep-service")
public interface SalesRepProxy {

    @GetMapping("/salesRep/check/{salesRepId}") // Change to sales-rep
    boolean checkSalesRep(@PathVariable Long salesRepId);
}
