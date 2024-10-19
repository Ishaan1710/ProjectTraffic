package com.example.traffic_prediction.repository;

import com.example.traffic_prediction.model.TrafficData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrafficDataRepository extends JpaRepository<TrafficData, Long> {
}