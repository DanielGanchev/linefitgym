package com.appfitgym.model.dto.country;

import com.appfitgym.model.dto.country.CountryLoadDto;

import java.util.List;

public  class FoodResponse {
    private List<FoodResponseDto> dishes;

    public List<FoodResponseDto> getDishes() {
        return dishes;
    }

    public FoodResponse setDishes(List<FoodResponseDto> dishes) {
        this.dishes = dishes;
        return this;
    }
}