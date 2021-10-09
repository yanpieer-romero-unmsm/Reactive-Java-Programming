package com.theyan.coffeeservice;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest
class CoffeeServiceApplicationTests {
    @Autowired
    private CoffeeService service;

    @Test
    public void getOrdersTake10() {
        String coffeeId = service.getAllCoffees().blockFirst().getId();

        StepVerifier.withVirtualTime(() -> service.getOrders(coffeeId).take(10))
                .thenAwait(Duration.ofSeconds(10))
                .expectNextCount(10)
                .verifyComplete();
    }

}
