package com.fewservices.vehicleaggregator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class LocationClient {

    private static final Logger logger = LoggerFactory.getLogger(LocationClient.class);

    private final WebClient busWebClient;
    private final WebClient trainWebClient;

    @Autowired
    public LocationClient(WebClient.Builder webClientBuilder,
                          @Value("${bus.location.service.url}") String busServiceUrl,
                          @Value("${train.location.service.url}") String trainServiceUrl) {
        this.busWebClient = webClientBuilder.baseUrl(busServiceUrl).build();
        this.trainWebClient = webClientBuilder.baseUrl(trainServiceUrl).build();
    }

    public Flux<LocationEvent> getBusLocationStream() {
        return busWebClient.get()
                .uri("/location-stream")
                .retrieve()
                .bodyToFlux(LocationEvent.class)
                .map(event -> {
                    event.setType("BUS");
                    return event;
                })
                .doOnError(error -> logger.error("Error fetching bus location stream: {}", error.getMessage()));
    }

    public Flux<LocationEvent> getTrainLocationStream() {
        return trainWebClient.get()
                .uri("/location-stream")
                .retrieve()
                .bodyToFlux(LocationEvent.class)
                .map(event -> {
                    event.setType("TRAIN");
                    return event;
                });
    }
}
