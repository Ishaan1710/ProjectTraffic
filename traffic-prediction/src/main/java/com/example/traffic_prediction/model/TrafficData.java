package com.example.traffic_prediction.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TrafficData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;
    private String areaName;
    private String roadName;
    private int trafficVolume;
    private double averageSpeed;
    private double travelTimeIndex;
    private int congestionLevel;
    private int roadCapacityUtilization;
    private int incidentReports;
    private double environmentalImpact;
    private double publicTransportUsage;
    private double trafficSignalCompliance;
    private double parkingUsage;
    private int pedestrianAndCyclistCount;
    private String weatherConditions;
    private String roadworkAndConstructionActivity;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public String getRoadName() {
        return roadName;
    }
    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }
    public int getTrafficVolume() {
        return trafficVolume;
    }
    public void setTrafficVolume(int trafficVolume) {
        this.trafficVolume = trafficVolume;
    }
    public double getAverageSpeed() {
        return averageSpeed;
    }
    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
    public double getTravelTimeIndex() {
        return travelTimeIndex;
    }
    public void setTravelTimeIndex(double travelTimeIndex) {
        this.travelTimeIndex = travelTimeIndex;
    }
    public int getCongestionLevel() {
        return congestionLevel;
    }
    public void setCongestionLevel(int congestionLevel) {
        this.congestionLevel = congestionLevel;
    }
    public int getRoadCapacityUtilization() {
        return roadCapacityUtilization;
    }
    public void setRoadCapacityUtilization(int roadCapacityUtilization) {
        this.roadCapacityUtilization = roadCapacityUtilization;
    }
    public int getIncidentReports() {
        return incidentReports;
    }
    public void setIncidentReports(int incidentReports) {
        this.incidentReports = incidentReports;
    }
    public double getEnvironmentalImpact() {
        return environmentalImpact;
    }
    public void setEnvironmentalImpact(double environmentalImpact) {
        this.environmentalImpact = environmentalImpact;
    }
    public double getPublicTransportUsage() {
        return publicTransportUsage;
    }
    public void setPublicTransportUsage(double publicTransportUsage) {
        this.publicTransportUsage = publicTransportUsage;
    }
    public double getTrafficSignalCompliance() {
        return trafficSignalCompliance;
    }
    public void setTrafficSignalCompliance(double trafficSignalCompliance) {
        this.trafficSignalCompliance = trafficSignalCompliance;
    }
    public double getParkingUsage() {
        return parkingUsage;
    }
    public void setParkingUsage(double parkingUsage) {
        this.parkingUsage = parkingUsage;
    }
    public int getPedestrianAndCyclistCount() {
        return pedestrianAndCyclistCount;
    }
    public void setPedestrianAndCyclistCount(int pedestrianAndCyclistCount) {
        this.pedestrianAndCyclistCount = pedestrianAndCyclistCount;
    }
    public String getWeatherConditions() {
        return weatherConditions;
    }
    public void setWeatherConditions(String weatherConditions) {
        this.weatherConditions = weatherConditions;
    }
    public String getRoadworkAndConstructionActivity() {
        return roadworkAndConstructionActivity;
    }
    public void setRoadworkAndConstructionActivity(String roadworkAndConstructionActivity) {
        this.roadworkAndConstructionActivity = roadworkAndConstructionActivity;
    }

    // Getters and Setters
    // Add necessary constructors
}
