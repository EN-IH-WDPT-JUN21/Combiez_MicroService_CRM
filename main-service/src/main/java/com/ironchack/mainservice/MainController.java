package com.ironchack.mainservice;

import com.ironchack.mainservice.dto.ConvertLeadDTO;
import com.ironchack.mainservice.dto.DecisionMakerDTO;
import com.ironchack.mainservice.dto.OpportunityDTO;
import com.ironchack.mainservice.service.MainService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    private final MainService mainService;

    public MainController(@Qualifier("main") MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping("/convert/{id}")
    public String convertLead(@PathVariable Long id, @RequestBody ConvertLeadDTO convertLeadDTO) {
        return mainService.convertLead(id, convertLeadDTO);
    }

}
