package com.example.traffic_prediction.model;

public class TrafficRequest {
    private String date;
    private String areaName;
    private String roadName;

    public TrafficRequest(String date, String areaName, String roadName){
        this.date = date;
        this.areaName = areaName;
        this.roadName = roadName;
    }

    public String getDateTime() {
        return date;
    }

    public void setDateTime(String date) {
        this.date = date;
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
}
