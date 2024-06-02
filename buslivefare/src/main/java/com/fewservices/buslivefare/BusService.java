package com.fewservices.buslivefare;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public class BusService {

    private final Random random = new Random();

    public Mono<BusFare> getFare(Long id) {
        if (random.nextBoolean()) {
            return Mono.just(new BusFare(id, 99));
        } else {
            return Mono.error(new RuntimeException("Failed to fetch fare data"));
        }
    }
}

