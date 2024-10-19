package com.example.traffic_prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import com.example.traffic_prediction.service.*;

@SpringBootApplication
public class TrafficPredictionApplication {
    
    // @Autowired
    // private CsvToDatabaseService csvToDatabaseService;
	public static void main(String[] args) {
		SpringApplication.run(TrafficPredictionApplication.class, args);
	}


    // @PostConstruct
    // public void init() {
    //     csvToDatabaseService.loadCsvData("D:\\Project-I\\traffic-prediction\\Banglore_traffic_Dataset.csv");
    // }
}

