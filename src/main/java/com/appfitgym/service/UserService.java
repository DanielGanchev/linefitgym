package com.appfitgym.service;

import com.appfitgym.model.dto.*;

import java.util.Optional;

public interface UserService {

    boolean register(UserRegistrationDto userRegistrationDto);





    Optional<UserUpdateValidationDto> getUserDetails(Long id);

    void updateUser(Long id, UserUpdateValidationDto userUpdate);
}
