package com.fewservices.vehicleaggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/")
public class Controller {

    private final BusService busService;
    private final LocationService vehicleLocationService;

    @Autowired
    public Controller(BusService busService, LocationService vehicleLocationService) {
        this.busService = busService;
        this.vehicleLocationService = vehicleLocationService;
    }

    @GetMapping(value = "buses", produces = "application/json")
    public Flux<Bus> getAllBuses() {
        return busService.getAllBuses();
    }

    @GetMapping(value = "vehicle-location-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<LocationEvent> getVehicleLocationStream() {
        return vehicleLocationService.getVehicleLocationStream();
    }
}
