package com.ironhack.microservice_homework_account.sampleData;

import com.github.javafaker.Faker;
import com.ironhack.microservice_homework_account.AccountRepository;
import com.ironhack.microservice_homework_account.dao.Account;
import com.ironhack.microservice_homework_account.dao.enums.Industry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final Faker faker;

    public SampleDataLoader(AccountRepository accountRepository, Faker faker) {
        this.accountRepository = accountRepository;
        this.faker = faker;
    }

    public List<Long> generateRandomLongBounded() {
        Long leftLimit = 1L;
        Long rightLimit = 10L;
        int operationLimit = 3;
        List<Long> randomNumberList = new ArrayList<>();
        for (int i = 0; i < (Math.random() * operationLimit); i++) {
            randomNumberList.add(leftLimit + (long) (Math.random() * (rightLimit - leftLimit)));
        }
        return randomNumberList;
    }

    @Override
    public void run(String... args) {

        List<Account> sampleData = IntStream.rangeClosed(1, 6)
                .mapToObj(i -> new Account(
                        Industry.values()[(int)(Math.random()*Industry.values().length)],
                        faker.number().numberBetween(10,5000),
                        faker.country().capital(),
                        faker.country().name(),
                        generateRandomLongBounded(),
                        generateRandomLongBounded()
                        ))
                        .collect(Collectors.toList());

        accountRepository.saveAll(sampleData);

    }
}
