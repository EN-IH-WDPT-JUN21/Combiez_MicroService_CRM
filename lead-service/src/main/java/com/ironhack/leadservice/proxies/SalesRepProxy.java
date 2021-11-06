package com.ironhack.leadservice.proxies;

import com.ironhack.leadservice.dto.SalesRepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotBlank;
import java.util.List;

@FeignClient("sales-rep-service")
public interface SalesRepProxy {

    @GetMapping("/salesRep/check/{salesRepId}") // Change to sales-rep
    boolean checkSalesRep(@PathVariable Long salesRepId);
}
