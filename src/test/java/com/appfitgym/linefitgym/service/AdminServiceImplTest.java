package com.appfitgym.linefitgym.service;

import com.appfitgym.config.UserSpecification;
import com.appfitgym.model.dto.UserDetailsAdminPage;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.UserRole;
import com.appfitgym.model.entities.country.City;
import com.appfitgym.model.entities.country.Country;
import com.appfitgym.model.entities.mail.VerificationToken;
import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.repository.VerificationTokenRepository;
import com.appfitgym.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

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
        Page<UserEntity> userEntityPage = new PageImpl<>(Arrays.asList(userEntity, userEntity2));

        when(userRepository.findAllWithoutAdminEveryStatus(pageable)).thenReturn(userEntityPage);

        Page<UserDetailsAdminPage> result = adminService.findAllUsers(pageable);

        assertEquals(userEntityPage.getSize(), result.getSize());
    }




    @Test
    void shouldDeleteUser() {
        Long userId = 1L;

        adminService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void shouldReturnApprovedWhenUserIsActive() {
        UserEntity userEntity = new UserEntity();
        userEntity.setActive(true);

        String status = AdminServiceImpl.statusOfUser(userEntity, verificationTokenRepository);

        assertEquals("approved", status);
    }

    @Test
    void shouldReturnPendingWhenUserIsNotActiveAndTokenExists() {

        userEntity.setActive(false);


        when(verificationTokenRepository.findByUser(userEntity)).thenReturn(Optional.of(new VerificationToken()));

        String status = AdminServiceImpl.statusOfUser(userEntity, verificationTokenRepository);

        assertEquals("pending", status);
    }

    @Test
    void shouldReturnDeclineWhenUserIsNotActiveAndTokenDoesNotExist() {

        userEntity.setActive(false);
        when(verificationTokenRepository.findByUser(userEntity)).thenReturn(Optional.empty());

        String status = AdminServiceImpl.statusOfUser(userEntity, verificationTokenRepository);

        assertEquals("decline", status);
    }


    @Test
    void shouldSearchUsers() {

        String query = "test";
        String field = "username";
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "username"));

        Page<UserEntity> userEntityPage = new PageImpl<>(Collections.singletonList(userEntity));

        when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(userEntityPage);


        Page<UserDetailsAdminPage> result = adminService.searchUsers(query, field, pageable);

  
        assertEquals(1, result.getSize());
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