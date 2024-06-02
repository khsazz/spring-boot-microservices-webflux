package com.fewservices.busfallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buses")
public class BusController {

    @GetMapping("/{id}/fare")
    public BusFare getBusFare(@PathVariable Long id) {
        // Hardcoded response for demonstration purposes
        return new BusFare(id, 201);
    }
}

