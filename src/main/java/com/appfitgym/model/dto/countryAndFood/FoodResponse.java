package com.appfitgym.model.dto.countryAndFood;

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