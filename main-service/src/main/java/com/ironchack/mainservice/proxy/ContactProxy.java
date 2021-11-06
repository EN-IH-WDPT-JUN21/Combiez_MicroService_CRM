package com.ironchack.mainservice.proxy;


import com.ironchack.mainservice.dto.DecisionMakerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("contact-service")
public interface ContactProxy {

    @PostMapping("/contact/new")
    DecisionMakerDTO createContact(@RequestBody DecisionMakerDTO decisionMakerDTO);
    // While the passed decisionMakerDTO is a lead, the id is ignored by the receiving service
    // and creates a new contact with same info but new id
}
