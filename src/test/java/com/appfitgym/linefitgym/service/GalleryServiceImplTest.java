package com.appfitgym.linefitgym.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.appfitgym.model.dto.RandomUserDto;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.UserRole;
import com.appfitgym.model.entities.country.City;
import com.appfitgym.model.entities.country.Country;
import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.service.impl.GalleryServiceImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

public class GalleryServiceImplTest {

    @InjectMocks
    private GalleryServiceImpl galleryService;

    @Mock
    private UserRepository userRepository;

    private UserEntity userEntity;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        galleryService = new GalleryServiceImpl(userRepository, modelMapper);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("user");
        userEntity.setFirstName("firstName");
        userEntity.setLastName("lastName");
        userEntity.setBirthDate(LocalDate.now());
        userEntity.setSexEnum(SexEnum.MALE);
        userEntity.setPhoneNumber("1234567890");
        userEntity.setEmail("coltarq@gmail.com");
        userEntity.setActive(true);

        userEntity.setCreatedOn(LocalDateTime.now());


        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.COACH);
        userEntity.setRoles(Collections.singletonList(userRole));

        City city = new City();
        city.setId(1L);
        userEntity.setCity(city);

        Country country = new Country();
        country.setId(1L);
        userEntity.setCountry(country);

        userEntity.setProfilePicture("profilePicturePath");


    }

    @Test
    void shouldConvertToDto() {

       UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(1L);
        userEntity1.setUsername("user");
        userEntity1.setFirstName("firstName");
        userEntity1.setLastName("lastName");
        userEntity1.setBirthDate(LocalDate.now());
        userEntity1.setSexEnum(SexEnum.MALE);
        userEntity1.setPhoneNumber("1234567890");
        userEntity1.setEmail("coltarq@gmail.com");
        userEntity1.setActive(true);

        userEntity1.setCreatedOn(LocalDateTime.now());


        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.TRAINEE);
        userEntity1.setRoles(Collections.singletonList(userRole));

        City city = new City();
        city.setId(1L);
        userEntity1.setCity(city);

        Country country = new Country();
        country.setId(1L);
        userEntity1.setCountry(country);

        userEntity1.setProfilePicture("profilePicturePath");

        RandomUserDto randomUserDto = galleryService.convertToDto(userEntity);
        RandomUserDto randomUserDto1 = galleryService.convertToDto(userEntity1);

        assertEquals(userEntity.getId(), randomUserDto.getId());
        assertEquals(userEntity1.getId(), randomUserDto1.getId());

    }




}