package com.ironchack.mainservice.controller;

import com.ironchack.mainservice.dto.ConvertLeadDTO;
import com.ironchack.mainservice.dto.DecisionMakerDTO;
import com.ironchack.mainservice.dto.OpportunityDTO;
import com.ironchack.mainservice.service.MainService;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    private final MainService mainService;

    public MainController(@Qualifier("main") MainService mainService) {
        this.mainService = mainService;
    }

    Logger logger = LoggerFactory.getLogger("MainServiceApplication.class");

    @PostMapping("/convert/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Retry(name = "lead-service", fallbackMethod = "getFallbackMethod")
    public String convertLead(@PathVariable Long id, @RequestBody ConvertLeadDTO convertLeadDTO) {
        return mainService.convertLead(id, convertLeadDTO);
    }

    // Fallback Method

    public String getFallbackMethod(Exception e) {
        logger.info("Fallback method called.");
        return "This action cannot be performed at this moment. Please, try again later.";
    }

}
