package com.ironchack.mainservice.proxy;

import com.ironchack.mainservice.dto.OpportunityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("opportunity-service")
public interface OpportunityProxy {

    @PostMapping("/opportunities/create")
    OpportunityDTO create(OpportunityDTO opportunityDTO);
}
