package com.ironhack.opportunityservice.sampleData;

import com.github.javafaker.Faker;
import com.ironhack.opportunityservice.dao.Opportunity;
import com.ironhack.opportunityservice.enums.Truck;
import com.ironhack.opportunityservice.repositories.OpportunityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final OpportunityRepository opportunityRepository;
    private final Faker faker;

    public SampleDataLoader(OpportunityRepository opportunityRepository, Faker faker) {
        this.opportunityRepository = opportunityRepository;
        this.faker = faker;
    }

    public Long generateRandomNumber() {
        Long leftLimit = 1L;
        Long rightLimit = 10L;
        Long randomNumber = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return randomNumber;
    }

    @Override
    public void run(String... args) {

        List<Opportunity> sampleData = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> new Opportunity(
                        Truck.values()[(int)(Math.random()*Truck.values().length)],
                        faker.number().numberBetween(1,150),
                        generateRandomNumber(),
                        generateRandomNumber(),
                        generateRandomNumber()
                ))
                .collect(Collectors.toList());

        opportunityRepository.saveAll(sampleData);

    }
}
