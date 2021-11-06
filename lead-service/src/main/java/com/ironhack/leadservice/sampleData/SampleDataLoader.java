package com.ironhack.leadservice.sampleData;

import com.github.javafaker.Faker;
import com.ironhack.leadservice.dao.Lead;
import com.ironhack.leadservice.repositories.LeadRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final LeadRepository leadRepository;
    private final Faker faker;

    public SampleDataLoader(LeadRepository leadRepository, Faker faker) {
        this.leadRepository = leadRepository;
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

        List<Lead> sampleData = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new Lead(
                        faker.name().firstName() + " " + faker.name().lastName(),
                        faker.phoneNumber().cellPhone(),
                        faker.internet().emailAddress(),
                        faker.company().name(),
                        generateRandomNumber()
                        ))
                        .collect(Collectors.toList());

        leadRepository.saveAll(sampleData);

    }
}
