package com.example.traffic_prediction.controller;
import com.example.traffic_prediction.model.TrafficData;
import com.example.traffic_prediction.model.TrafficRequest;
import com.example.traffic_prediction.service.TrafficDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traffic")
public class TrafficDataController {

    private final TrafficDataService service;
    TrafficRequest trafficRequest;
    public TrafficDataController(TrafficDataService service) {
        this.service = service;
    }
    
    @PostMapping("/predict")
    // @RequestParam(value = "date") String date, 
    public float predictTrafficCongestion(@RequestBody TrafficRequest trafficRequest) {
        // Call service to make the prediction
        float prediction = service.predictTraffic(trafficRequest);
        System.out.println(prediction);
        return prediction;
    }

    @GetMapping("/predict")
    public String homeTraffic(){
        String homeMessage = "Hello, This is the get method.";
        return homeMessage;
    }
}
