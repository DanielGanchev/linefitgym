package com.appfitgym.web;

import com.appfitgym.model.dto.UserUpdateValidationDto;
import com.appfitgym.model.dto.country.CountryLoadDto;
import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.service.CountryService;
import com.appfitgym.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserUpdateController {

  private final UserService userService;
  private final CountryService countryService;

  public UserUpdateController(UserService userService, CountryService countryService) {
    this.userService = userService;
    this.countryService = countryService;
  }

  @ModelAttribute("sexEnums")
  public SexEnum[] sexEnums() {

    return SexEnum.values();
  }

  @ModelAttribute("roles")
  public List<UserRoleEnum> roles() {
    return Arrays.stream(UserRoleEnum.values())
        .filter(role -> !role.equals(UserRoleEnum.ADMIN))
        .collect(Collectors.toList());
  }

  @ModelAttribute("countries")
  public List<CountryLoadDto> countries() {
    return countryService.getAllCountries();
  }

  @GetMapping("/update/{userId}")
  public ModelAndView updateUser(@PathVariable("userId") Long userId, Model model) {
    if (!model.containsAttribute("userUpdate")) {
      UserUpdateValidationDto userUpdate = userService.getUserDetails(userId)
              .orElseThrow(() -> new UsernameNotFoundException("User with name " + userId + " not found!"));
      model.addAttribute("userUpdate", userUpdate);
    }
    return new ModelAndView("user-details");
  }


  @PatchMapping("/update/{userId}")
  public ModelAndView updateUser(
          @PathVariable("userId") Long userId,
          @ModelAttribute("userUpdate") @Valid UserUpdateValidationDto userUpdate,
          BindingResult result,
       RedirectAttributes redirectAttributes   ) throws IOException {

    userUpdate.setId(userId);
    userUpdate.setProfilePicturePath(userService.getUserDetails(userId).get().getProfilePicturePath());

    if (result.hasErrors()) {
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userUpdate", result);
      redirectAttributes.addFlashAttribute("userUpdate", userUpdate);
      return new ModelAndView("redirect:/users/update/" + userId);
    }

    userService.updateUser(userId, userUpdate);

    return new ModelAndView("redirect:/users/update/" + userId);
  }


}
