package com.fewservices.vehicleaggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;



@Service
public class BusService {

    private final BusClient busClient;

    @Autowired
    public BusService(BusClient busClient) {
        this.busClient = busClient;
    }

    public Flux<Bus> getAllBuses() {
        return busClient.getBusMetadata()
                .flatMap(bus -> busClient.getLiveBusFare(bus.getId())
                        .onErrorResume(e -> busClient.getFallbackBusFare(bus.getId()))
                        .map(BusFare::getFare)
                        .map(fare -> {
                            bus.setFare(fare);
                            return bus;
                        })
                );
    }
}


