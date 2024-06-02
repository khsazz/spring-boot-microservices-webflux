package com.fewservices.trainlocation;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "/location-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<LocationData> streamLocations() {
        return locationService.getLocationStream();
    }
}

