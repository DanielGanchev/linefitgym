package com.appfitgym.web;

import com.appfitgym.model.dto.UserDetailsViewDto;
import com.appfitgym.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/view/users")
public class UserDetailsController {

  private final UserService userService;

  public UserDetailsController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/details/{id}")
  public ModelAndView details(@PathVariable Long id) {
    UserDetailsViewDto userDetailsViewDto = userService.getUserViewDetails(id);

    ModelAndView modelAndView = new ModelAndView("user-details-view");

    modelAndView.addObject("user", userDetailsViewDto);

    return modelAndView;
  }
}
