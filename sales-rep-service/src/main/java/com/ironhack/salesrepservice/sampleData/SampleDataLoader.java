package com.ironhack.salesrepservice.sampleData;

import com.github.javafaker.Faker;
import com.ironhack.salesrepservice.dao.SalesRep;
import com.ironhack.salesrepservice.repository.SalesRepRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final SalesRepRepository salesRepRepository;
    private final Faker faker;

    public SampleDataLoader(SalesRepRepository salesRepRepository, Faker faker) {
        this.salesRepRepository = salesRepRepository;
        this.faker = faker;
    }

    @Override
    public void run(String... args) {

        List<SalesRep> sampleData = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new SalesRep(
                        faker.name().firstName() + " " + faker.name().lastName()))
                        .collect(Collectors.toList());

        salesRepRepository.saveAll(sampleData);

    }
}
