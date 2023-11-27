package com.appfitgym.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@Controller
public class LoginController {



  @GetMapping("/users/login")
  public String login() {

    return "login";
  }



  @PostMapping("/users/login-error")
  public String failedLogin(@ModelAttribute("username") String username, Model model) {

    System.out.println("Login failed for user " + username);
    model.addAttribute("username", username);
    model.addAttribute("bad_credentials", true);
    return "login";
  }




}
