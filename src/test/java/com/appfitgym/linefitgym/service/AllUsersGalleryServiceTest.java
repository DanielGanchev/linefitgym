package com.appfitgym.linefitgym.service;

import com.appfitgym.model.dto.GalleryUserDetailsDto;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.UserRole;
import com.appfitgym.model.entities.country.City;
import com.appfitgym.model.entities.country.Country;
import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.service.impl.AllUsersGalleryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AllUsersGalleryServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private AllUsersGalleryService allUsersGalleryService;

  private UserEntity userEntity;
  private UserEntity userEntity2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    createDummyEntities();
  }



  @Test
    void shouldFindAllUsers() {
        Pageable pageable = mock(Pageable.class);

      Page<UserEntity> userEntityPage = new PageImpl<>(Collections.singletonList(userEntity));

      when(userRepository.findAllWithoutAdminOnlyActiveStatus(pageable)).thenReturn(userEntityPage);

      Page<GalleryUserDetailsDto> result = allUsersGalleryService.findAllUsers(pageable);

      assertEquals(1, result.getSize());
    }

  @Test
  void shouldSearchUsersInGallery() {
    Pageable pageable = mock(Pageable.class);

    Page<UserEntity> userEntityPage = new PageImpl<>(Arrays.asList(userEntity));

    when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(userEntityPage);

    Page<GalleryUserDetailsDto> result =
        allUsersGalleryService.searchUsersInGallery("query", "field", pageable);

    assertEquals(userEntityPage.getSize(), result.getSize());
  }

  @Test
  void testFindTopCoaches() {

    List<UserEntity> coaches = Arrays.asList(userEntity, userEntity2);
    when(userRepository.findTopCoaches()).thenReturn(coaches);


    List<GalleryUserDetailsDto> result = allUsersGalleryService.findTopCoaches();


    assertEquals(2, result.size());
  }

  private void createDummyEntities() {
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
    userEntity.setRoles(Arrays.asList(userRole));
    City city = new City();
    city.setId(1L);
    userEntity.setCity(city);
    Country country = new Country();
    country.setId(1L);
    userEntity.setCountry(country);
    userEntity.setProfilePicture("profilePicturePath");

    userEntity2 = new UserEntity();
    userEntity2.setId(1L);
    userEntity2.setUsername("user");
    userEntity2.setFirstName("firstName");
    userEntity2.setLastName("lastName");
    userEntity2.setBirthDate(LocalDate.now());
    userEntity2.setSexEnum(SexEnum.MALE);
    userEntity2.setPhoneNumber("1234567890");
    userEntity2.setEmail("coltarq@gmail.com");
    userEntity2.setActive(true);
    userEntity2.setCreatedOn(LocalDateTime.now());

    UserRole userRole2 = new UserRole();
    userRole2.setRole(UserRoleEnum.ADMIN);
    userEntity2.setRoles(List.of(userRole2));

    City city2 = new City();
    city2.setId(2L);
    userEntity2.setCity(city);

    Country country2 = new Country();
    country2.setId(2L);
    userEntity2.setCountry(country);
    userEntity2.setProfilePicture("profilePicturePath");
  }
}
