package com.appfitgym.service;

import com.appfitgym.model.dto.RandomUserDto;
import com.appfitgym.model.entities.UserEntity;

import java.util.List;

public interface GalleryService {


    RandomUserDto convertToDto(UserEntity userEntity);

    RandomUserDto getRandomUser();

    List<RandomUserDto> getRandomUsers();
}
