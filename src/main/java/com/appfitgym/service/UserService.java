package com.appfitgym.service;

import com.appfitgym.model.dto.*;
import com.appfitgym.model.entities.UserEntity;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    UserEntity register(UserRegistrationDto userRegistrationDto) throws IOException;





    Optional<UserUpdateValidationDto> getUserDetails(Long id);

    void updateUser(Long id, UserUpdateValidationDto userUpdate) throws IOException;

    void saveUserVerificationToken(UserEntity theUser, String verificationToken);

    String validateToken(String theToken);

    UserDetailsViewDto getUserViewDetails(Long id);



}
