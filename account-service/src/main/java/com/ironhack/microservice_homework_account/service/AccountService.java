package com.ironhack.microservice_homework_account.service;

import com.ironhack.microservice_homework_account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Double findMedianEmployeeCount() {
        return getMedian(accountRepository.findMedianEmployeeCount());
    }

    public Double getMedian(int[] intArray){
        try {
            int sizeOfArray = intArray.length;
            if (sizeOfArray % 2 == 1) {
                return Double.valueOf(intArray[(sizeOfArray + 1) / 2 - 1]);
            } else {
                return Double.valueOf(intArray[sizeOfArray / 2 - 1] + intArray[sizeOfArray / 2]) / 2;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return 0.0;
        }
    }


}
