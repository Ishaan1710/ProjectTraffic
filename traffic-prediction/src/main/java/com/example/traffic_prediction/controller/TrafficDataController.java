package com.example.traffic_prediction.controller;
import com.example.traffic_prediction.model.TrafficData;
import com.example.traffic_prediction.service.TrafficDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traffic")
public class TrafficDataController {

    private final TrafficDataService service;

    public TrafficDataController(TrafficDataService service) {
        this.service = service;
    }
    
    @PostMapping("/predict")
    public String predictTrafficCongestion(@RequestParam String date, @RequestParam String areaName, @RequestParam String roadName) {
        // Call service to make the prediction
        String prediction = service.predictTraffic(date, areaName, roadName);
        return "Predicted Traffic Congestion Level: " + prediction;
    }
}
