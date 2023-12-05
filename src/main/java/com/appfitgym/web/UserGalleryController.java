package com.appfitgym.web;

import com.appfitgym.model.dto.RandomUserDto;
import com.appfitgym.service.GalleryService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/gallery")
public class UserGalleryController {

  private final GalleryService galleryService;

  public UserGalleryController(GalleryService galleryService) {
    this.galleryService = galleryService;
  }

  @GetMapping("")
  public ModelAndView gallery(Model model) {

    RandomUserDto randomUserDto = galleryService.getRandomUser();
    List<RandomUserDto> randomUsers = galleryService.getRandomUsers();

    model.addAttribute("randomUser", randomUserDto);
    model.addAttribute("randomUsers", randomUsers);

    return new ModelAndView("details");
  }
}
