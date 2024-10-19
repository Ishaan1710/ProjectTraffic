package com.example.traffic_prediction.service;

import com.example.traffic_prediction.model.TrafficData;
import com.example.traffic_prediction.model.TrafficRequest;
import com.example.traffic_prediction.repository.TrafficDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrafficDataService {

    //private final TrafficDataRepository repository;

    public String predictTraffic(TrafficRequest trafficRequest) {
        // String date,  You will call your Python model or the machine learning model here

        // Example: Integrate your ML model or call an external API
        // For now, let's return a placeholder response
        return "Low Congestion";  // This will be replaced by actual ML model prediction
    }
}
