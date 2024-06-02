package com.fewservices.buslivefare;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class BusController {

    private final BusService busService;
    private final LocationService locationService;

    public BusController(BusService busService, LocationService locationService) {
        this.busService = busService;
        this.locationService = locationService;
    }

    @GetMapping("buses/{id}/fare")
    public Mono<ResponseEntity<BusFare>> getFare(@PathVariable Long id) {
        return busService.getFare(id)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body(new BusFare(id, -1))));
    }

    @GetMapping(value = "location-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<LocationData> streamLocations() {
        return locationService.getLocationStream();
    }
}

