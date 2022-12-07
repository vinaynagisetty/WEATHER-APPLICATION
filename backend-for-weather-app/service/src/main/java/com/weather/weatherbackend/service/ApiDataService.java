package com.weather.weatherbackend.service;

import com.weather.weatherbackend.domain.cityDataSchema.City;
import com.weather.weatherbackend.domain.dailyDataSchema.Daily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiDataService {
    private final RestTemplate restTemplate;

    @Autowired
    public ApiDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public City consumeCityApi(String api){
        return restTemplate.getForObject(api,City.class);
    }
    public Daily consumeDailyDataApi(String api){
        return restTemplate.getForObject(api, Daily.class);
    }
}
