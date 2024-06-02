package com.fewservices.busmetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metadata")
public class BusController {

    private final BusRepository busRepository;

    @Autowired
    public BusController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @GetMapping(produces = "application/json")
    public List<Bus> getAllBusMetadata() {
        return busRepository.findAll();
    }
}
