package com.ironchack.mainservice.proxy;

import com.ironchack.mainservice.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("account-service")
public interface AccountProxy {

    @PostMapping("/")
    AccountDTO create(AccountDTO accountDTO);
}
