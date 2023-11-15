package com.appfitgym.service;

import com.appfitgym.model.dto.UserLoginBindingModel;
import com.appfitgym.model.dto.UserRegistrationDto;
import com.appfitgym.model.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {

    void register(UserRegistrationDto userRegistrationDto);


}
