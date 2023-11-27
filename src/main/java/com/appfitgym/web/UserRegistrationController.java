package com.appfitgym.web;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.appfitgym.model.dto.*;
import com.appfitgym.model.dto.country.CountryLoadDto;
import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.service.CountryService;
import com.appfitgym.service.UserService;
import jakarta.validation.Valid;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserService userService;
    private final CountryService countryService;

    private final UserDetailsService userDetailsService;









    public UserRegistrationController(UserService userService, CountryService countryService,

                                      UserDetailsService userDetailsService
                                   ) {
        this.userService = userService;
        this.countryService = countryService;

        this.userDetailsService = userDetailsService;




    }



    @GetMapping("/register")
    public ModelAndView register(
            @ModelAttribute("userRegisterBindingModel") UserRegistrationDto userRegistrationDto) {

        return new ModelAndView("register");
    }

    @ModelAttribute("sexEnums")
    public SexEnum[] sexEnums(){

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

            BindingResult bindingResult)  {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("register");

      if (userRegistrationDto.profilePicture() == null
          || userRegistrationDto.profilePicture().isEmpty()) {
        modelAndView.addObject("hasProfilePictureError", true);
      }

            return new ModelAndView("register");
        }


        boolean hasSuccessfulRegistration = userService.register(userRegistrationDto);

        if (!hasSuccessfulRegistration) {
            ModelAndView modelAndView = new ModelAndView("register");

            modelAndView.addObject("hasRegistrationError", true);
            return modelAndView;
        }

        return new ModelAndView("redirect:/");
    }












}
