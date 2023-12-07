package com.appfitgym.web;

import com.appfitgym.interceptor.RateLimitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

  private final RateLimitService rateLimitService;

  public LoginController(RateLimitService rateLimitService) {
    this.rateLimitService = rateLimitService;
  }

  @GetMapping("/users/login")
  public String login() {

    return "login";
  }

  @PostMapping("/users/login-error")
  public String failedLogin(@ModelAttribute("username") String username, Model model) {


    model.addAttribute("username", username);
    model.addAttribute("bad_credentials", true);
    model.addAttribute("blocked", rateLimitService.isBlocked(username));
    return "login";
  }


}
