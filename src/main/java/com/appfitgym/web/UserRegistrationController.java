package com.appfitgym.web;

import com.appfitgym.event.RegistrationCompleteEvent;
import com.appfitgym.model.dto.*;
import com.appfitgym.model.dto.country.CountryLoadDto;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.mail.VerificationToken;
import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.repository.VerificationTokenRepository;
import com.appfitgym.service.CountryService;
import com.appfitgym.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/users")
public class UserRegistrationController {

  private final UserService userService;
  private final CountryService countryService;

  private final ApplicationEventPublisher publisher;

  private final VerificationTokenRepository tokenRepository;

  public UserRegistrationController(
      UserService userService,
      CountryService countryService,
      ApplicationEventPublisher publisher,
      VerificationTokenRepository tokenRepository) {
    this.userService = userService;
    this.countryService = countryService;

    this.publisher = publisher;
    this.tokenRepository = tokenRepository;
  }

  @GetMapping("/register")
  public ModelAndView register(
      @ModelAttribute("userRegisterBindingModel") UserRegistrationDto userRegistrationDto,
      Model model) {
    return new ModelAndView("register");
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

  @PostMapping("/register")
  public ModelAndView register(
      @ModelAttribute("userRegisterBindingModel") @Valid UserRegistrationDto userRegistrationDto,
      BindingResult bindingResult,
      final HttpServletRequest request)
      throws IOException {

    if (bindingResult.hasErrors()) {

      return new ModelAndView("register");
    }

    UserEntity savedUser = userService.register(userRegistrationDto);

    if (savedUser != null) {
      publisher.publishEvent(new RegistrationCompleteEvent(savedUser, applicationUrl(request)));
    }

    return new ModelAndView("redirect:/users/login");
  }

  public String applicationUrl(HttpServletRequest request) {
    return "http://"
        + request.getServerName()
        + ":"
        + request.getServerPort()
        + request.getContextPath();
  }

  @GetMapping("/register/verifyEmail")
  public ModelAndView verifyEmail(@RequestParam("token") String token) {
    VerificationToken theToken = tokenRepository.findByToken(token);
    ModelAndView modelAndView = new ModelAndView();
    if (theToken.getUser().isActive()) {
      modelAndView.setViewName("redirect:/users/login");
      modelAndView.addObject("message", "This account has already been verified, please, login.");
    } else {
      String verificationResult = userService.validateToken(token);
      if (verificationResult.equalsIgnoreCase("valid")) {
        modelAndView.setViewName("redirect:/users/login");
        modelAndView.addObject(
            "message", "Email verified successfully. Now you can login to your account");
      } else {
        modelAndView.setViewName("redirect:/users/login");
        modelAndView.addObject("message", "Invalid verification token");
      }
    }
    return modelAndView;
  }
}
