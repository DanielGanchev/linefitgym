package com.appfitgym.service.impl.fetchServices;

import com.appfitgym.model.dto.countryAndFood.FoodResponse;
import com.appfitgym.model.dto.countryAndFood.FoodResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class DietegramApiService {

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    public List<FoodResponseDto> getFoodData(String foodName) {
        String apiUrl = "https://dietagram.p.rapidapi.com/apiFood.php?name={foodName}&lang=en";
        apiUrl = apiUrl.replace("{foodName}", foodName);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", rapidApiKey);
        headers.set("X-RapidAPI-Host", "dietagram.p.rapidapi.com");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();
       ResponseEntity<FoodResponse> responseEntity = restTemplate.exchange(
               apiUrl,HttpMethod.GET,entity,FoodResponse.class);


        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            FoodResponse apiResponse = responseEntity.getBody();
            if (apiResponse != null) {
                return apiResponse.getDishes();
            }
    }
        return Collections.emptyList();
        }
}
