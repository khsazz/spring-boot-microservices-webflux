package com.fewservices.buslivefare;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LocationService {

    private final Random random = new Random();
    private final AtomicLong counter = new AtomicLong(1);

    public Flux<LocationData> getLocationStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> new LocationData(counter.getAndIncrement(), generateRandomLocation()))
                .take(5)
                .concatWith(Flux.error(new RuntimeException("Stream failed after emitting 5 items")));
    }

    private Location generateRandomLocation() {
        double lat = -90 + (90 - (-90)) * random.nextDouble();
        double lng = -180 + (180 - (-180)) * random.nextDouble();
        return new Location(lat, lng);
    }
}

