package com.appfitgym.web;

import com.appfitgym.model.dto.*;
import com.appfitgym.model.entities.LineFitGymUserDetails;
import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.service.CountryService;
import com.appfitgym.service.UserService;


import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserService userService;
    private final CountryService countryService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;







    public UserRegistrationController(UserService userService, CountryService countryService,

                                      UserDetailsService userDetailsService,
                                      AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.countryService = countryService;

        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;



    }



    @GetMapping("/register")
    public ModelAndView register(
            @ModelAttribute("userRegisterBindingModel") UserRegistrationDto userRegistrationDto,
            Model model
    ) {
        List<CountryLoadDto> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);

        List<UserRoleEnum> roles = Arrays.stream(UserRoleEnum.values())
                .filter(role -> !role.equals(UserRoleEnum.ADMIN))
                .collect(Collectors.toList());
        model.addAttribute("roles", roles);


        return new ModelAndView("register");
    }

    @ModelAttribute("sexEnums")
    public SexEnum[] sexEnums(){

        return SexEnum.values();
    }



    @PostMapping("/register")
    public ModelAndView register(
            @ModelAttribute("userRegisterBindingModel") @Valid UserRegistrationDto userRegistrationDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        userService.register(userRegistrationDto);

        return new ModelAndView("redirect:/");
    }












}
