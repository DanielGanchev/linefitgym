package com.appfitgym.web;

import com.appfitgym.model.dto.country.FoodResponseDto;
import com.appfitgym.service.impl.fetchServices.DietegramApiService;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/food")
public class FoodController {

  private final DietegramApiService dietegramApiService;

  public FoodController(DietegramApiService dietegramApiService) {
    this.dietegramApiService = dietegramApiService;
  }

  @GetMapping("/input")
  public ModelAndView loadFood(Model model) {

    model.addAttribute("foodData", null);

    return new ModelAndView("check-food");
  }

  @GetMapping("/table")
  public ModelAndView loadFoodTable(@RequestParam String foodName, Model model) {
    model.addAttribute("foodData", null);
    List<FoodResponseDto> foodData = dietegramApiService.getFoodData(foodName);
    model.addAttribute("foodDataSize", foodData.size());

    model.addAttribute("foodData", foodData);
    return new ModelAndView("check-food");
  }
}
