package com.fewservices.vehicleaggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class LocationService {

    private final LocationClient vehicleLocationClient;

    @Autowired
    public LocationService(LocationClient vehicleLocationClient) {
        this.vehicleLocationClient = vehicleLocationClient;
    }

    public Flux<LocationEvent> getVehicleLocationStream() {
        Flux<LocationEvent> busStream = vehicleLocationClient.getBusLocationStream()
                .retryWhen(Retry.backoff(Long.MAX_VALUE, Duration.ofSeconds(5)))
                .doOnError(error -> System.out.println("Error occurred: " + error));

        Flux<LocationEvent> trainStream = vehicleLocationClient.getTrainLocationStream();

        return Flux.merge(busStream, trainStream);
    }
}
