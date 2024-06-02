package com.fewservices.vehicleaggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class BusClient {

    private final WebClient metadataWebClient;
    private final WebClient liveFareWebClient;
    private final WebClient fallbackFareWebClient;

    @Autowired
    public BusClient(WebClient.Builder webClientBuilder,
                     @Value("${bus.metadata.service.url}") String busMetaServiceUrl,
                     @Value("${bus.location.service.url}") String busServiceUrl,
                     @Value("${bus.fallback.service.url}") String busFallbackServiceUrl) {
        this.metadataWebClient = webClientBuilder.baseUrl(busMetaServiceUrl).build();
        this.liveFareWebClient = webClientBuilder.baseUrl(busServiceUrl).build();
        this.fallbackFareWebClient = webClientBuilder.baseUrl(busFallbackServiceUrl).build();
    }

    public Flux<Bus> getBusMetadata() {
        return metadataWebClient.get()
                .uri("/metadata")
                .retrieve()
                .bodyToFlux(Bus.class);
    }

    public Mono<BusFare> getLiveBusFare(Long id) {
        return liveFareWebClient.get()
                .uri("/buses/{id}/fare", id)
                .retrieve()
                .bodyToMono(BusFare.class);
    }

    public Mono<BusFare> getFallbackBusFare(Long id) {
        return fallbackFareWebClient.get()
                .uri("/buses/{id}/fare", id)
                .retrieve()
                .bodyToMono(BusFare.class);
    }
}
