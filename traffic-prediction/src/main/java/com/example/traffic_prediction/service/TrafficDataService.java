package com.example.traffic_prediction.service;

import com.example.traffic_prediction.model.TrafficData;
import com.example.traffic_prediction.model.TrafficRequest;
import com.example.traffic_prediction.repository.TrafficDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class TrafficDataService {

    // private final TrafficDataRepository repository;

    // public String predictTraffic(TrafficRequest trafficRequest) {

    // RestTemplate restTemplate = new RestTemplate();
    // String url = "http://localhost:5000/predict?date=" +
    // trafficRequest.getDateTime() + "&areaName=" + trafficRequest.getAreaName() +
    // "&roadName=" + trafficRequest.getRoadName();

    // // Call the Python API and return the response
    // String result = restTemplate.getForObject(url, String.class);
    // return result;
    // }

    // public String predictTraffic(TrafficRequest trafficRequest) {

    //     // Create a RestTemplate instance
    //     RestTemplate restTemplate = new RestTemplate();

    //     // Define the target URL
    //     String url = "http://localhost:5000/predict";

    //     // Create headers for the request
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_JSON); // Set content type to JSON

    //     // Create the request body
    //     String requestBody = "{"
    //             + "\"date\": \"" + trafficRequest.getDateTime() + "\","
    //             + "\"area_name\": \"" + trafficRequest.getAreaName() + "\","
    //             + "\"road_name\": \"" + trafficRequest.getRoadName() + "\""
    //             + "}";

    //     // Create an HttpEntity containing the headers and the body
    //     HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    //     // Make the POST request and get the response
    //     ResponseEntity<String> response = restTemplate.exchange(
    //             url, HttpMethod.POST, requestEntity, String.class);

    //     // Return the response body as a String
    //     return response.getBody();
    // }


    public float predictTraffic(TrafficRequest trafficRequest) {

    // Create a RestTemplate instance
    RestTemplate restTemplate = new RestTemplate();

    // Define the target URL
    String url = "http://localhost:5000/predict_xgboost";

    // Create headers for the request
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);  // Set content type to JSON

    // Create the request body
    String requestBody = "{"
            + "\"date\": \"" + trafficRequest.getDateTime() + "\","
            + "\"area_name\": \"" + trafficRequest.getAreaName() + "\","
            + "\"road_name\": \"" + trafficRequest.getRoadName() + "\""
            + "}";

    // Create an HttpEntity containing the headers and the body
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    try {
        // Make the POST request and get the response as a String
        String response = restTemplate.postForObject(url, requestEntity, String.class);

        // Use Jackson's ObjectMapper to parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);

        // Extract the predicted congestion level from the response map
        float predictedCongestionLevel = Float.parseFloat(responseMap.get("predicted_congestion_level").toString());

        // Return the float value
        return predictedCongestionLevel;

    } catch (HttpClientErrorException e) {
        // Handle error responses from the API
        System.out.println("Error occurred while calling API: " + e.getMessage());
        return 0.0f;  // Return a default value or handle the error accordingly
    } catch (Exception e) {
        // Handle parsing or other exceptions
        System.out.println("Error parsing the API response: " + e.getMessage());
        return 0.0f;  // Return a default value or handle the error accordingly
    }
}
}
