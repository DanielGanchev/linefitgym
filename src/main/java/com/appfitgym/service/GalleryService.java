package com.appfitgym.service;

import com.appfitgym.model.dto.RandomUserDto;
import com.appfitgym.model.entities.UserEntity;

public interface GalleryService {


    RandomUserDto convertToDto(UserEntity userEntity);

    RandomUserDto getRandomUser();
}
