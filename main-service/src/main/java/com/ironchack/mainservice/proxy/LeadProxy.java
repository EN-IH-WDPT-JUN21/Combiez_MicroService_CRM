package com.ironchack.mainservice.proxy;

import com.ironchack.mainservice.dto.DecisionMakerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("lead-service")
public interface LeadProxy {

    @GetMapping("/lead/{id}")
    DecisionMakerDTO getLeadInfo(@PathVariable Long id);

    @DeleteMapping("/lead/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
    // For delete to work it must return something. Tried first with void and got error
}
