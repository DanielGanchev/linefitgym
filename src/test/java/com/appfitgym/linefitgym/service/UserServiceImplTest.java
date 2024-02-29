package com.appfitgym.linefitgym.service;

import com.appfitgym.model.dto.CustomerUserDetails;
import com.appfitgym.model.dto.UserDetailsViewDto;
import com.appfitgym.model.dto.UserRegistrationDto;
import com.appfitgym.model.dto.UserUpdateValidationDto;

import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.UserRole;
import com.appfitgym.model.entities.country.City;
import com.appfitgym.model.entities.country.Country;
import com.appfitgym.model.entities.mail.VerificationToken;
import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.repository.*;
import com.appfitgym.service.CloudinaryService;
import com.appfitgym.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

  @InjectMocks private UserServiceImpl userService;

  @Mock private UserRepository userRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @Mock private CountryRepository countryRepository;

  @Mock private CityRepository cityRepository;

  @Mock private UserRoleRepository userRoleRepository;


  @Mock
 private VerificationTokenRepository tokenRepository;
  @Mock private CloudinaryService cloudinaryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldRegisterUser() throws IOException {
    UserRegistrationDto userRegistrationDto =
        new UserRegistrationDto(
            "username",
            "firstName",
            "lastName",
            "email@example.com",
            "password",
            "password",
            LocalDate.now().minusYears(20),
            "1234567890",
            1L,
            UserRoleEnum.COACH,
            1L,
            SexEnum.MALE,
            null);

    City city = new City();
    city.setId(1L);

    UserRole userRole = new UserRole();
    userRole.setRole(UserRoleEnum.COACH);

    when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
    when(userRoleRepository.findByRole(UserRoleEnum.COACH)).thenReturn(Optional.of(userRole));
    when(passwordEncoder.encode(userRegistrationDto.password())).thenReturn("hashedPassword");

    UserEntity userEntity = new UserEntity();
    userEntity.setPassword("hashedPassword");
    when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

    UserEntity savedUser = userService.register(userRegistrationDto);

    verify(userRepository).save(any(UserEntity.class));
    assertEquals("hashedPassword", savedUser.getPassword());
  }

  @Test
  void shouldUpdateUser() throws IOException {
    UserUpdateValidationDto userUpdate = new UserUpdateValidationDto();
    userUpdate.setCityId(1L);

    City city = new City();
    city.setId(1L);
    city.setCountry(new Country());

    UserEntity userEntity = new UserEntity();
    when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
    when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

    userService.updateUser(1L, userUpdate);

    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void shouldGetLoggedUserId() {
      UserEntity userEntity = new UserEntity();
      userEntity.setId(1L);
      userEntity.setUsername("user");
      userEntity.setPassword("password");
      userEntity.setActive(true);
      userEntity.setRoles(new ArrayList<>());
      UserDetails principal = new CustomerUserDetails(userEntity);

      Authentication auth = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(auth);

    Long userId = UserServiceImpl.getLoggedUserId();

    assertEquals(1L, userId);
  }

    @Test
    void shouldGetUserDetails() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("user");
        userEntity.setFirstName("firstName");
        userEntity.setLastName("lastName");
        userEntity.setBirthDate(LocalDate.now());
        userEntity.setSexEnum(SexEnum.MALE);
        userEntity.setPhoneNumber("1234567890");
        userEntity.setEmail("coltarq@gmail.com");
        userEntity.setActive(true);


        City city = new City();
        city.setId(1L);
        userEntity.setCity(city);

        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.COACH);
        userEntity.setRoles(Collections.singletonList(userRole));

        Country country = new Country();
        country.setId(1L);
        userEntity.setCountry(country);

        userEntity.setProfilePicture("profilePicturePath");

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        Optional<UserUpdateValidationDto> userDetails = userService.getUserDetails(1L);

        assertTrue(userDetails.isPresent());
        assertEquals(1L, userDetails.get().getId());
    }



  @Test
  void shouldSaveUserVerificationToken() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(1L);

    userService.saveUserVerificationToken(userEntity, "token");

    verify(tokenRepository).save(any(VerificationToken.class));
  }

  @Test
  void shouldValidateToken() {
    VerificationToken token = new VerificationToken("token", new UserEntity());

    when(tokenRepository.findByToken("token")).thenReturn(token);

    String result = userService.validateToken("token");

    assertEquals("valid", result);
  }

  @Test
  void shouldGetUserViewDetails() {
      UserEntity userEntity = new UserEntity();
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

    when(userRepository.findByIdNotAdmin(1L)).thenReturn(Optional.of(userEntity));

    UserDetailsViewDto userDetails = userService.getUserViewDetails(1L);

    assertEquals(1L, userDetails.getId());
  }

    @Test
    void shouldReturnInvalidVerificationToken() {
        String theToken = "invalidToken";

        when(tokenRepository.findByToken(theToken)).thenReturn(null);

        String result = userService.validateToken(theToken);

        assertEquals("Invalid verification token", result);
    }

    @Test
    void shouldReturnTokenAlreadyExpired() {
        String theToken = "expiredToken";
        VerificationToken token = new VerificationToken();
        token.setExpirationTime(Calendar.getInstance().getTime());

        when(tokenRepository.findByToken(theToken)).thenReturn(token);

        String result = userService.validateToken(theToken);

        assertEquals("Token already expired", result);
    }


    @Test
    void shouldThrowUsernameNotFoundException() {
        Long userId = 1L;
        UserUpdateValidationDto userUpdate = new UserUpdateValidationDto();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.updateUser(userId, userUpdate));
    }

    @Test
    void shouldUpdateProfilePicture() throws IOException {
        Long userId = 1L;
        UserUpdateValidationDto userUpdate = new UserUpdateValidationDto();
        userUpdate.setCityId(1L);
        MultipartFile img = new MockMultipartFile("file", "hello.png", "image/png", "Hello, World!".getBytes());
        userUpdate.setProfilePicture(img);

        UserEntity userEntity = new UserEntity();
        userEntity.setProfilePicture("oldPicturePath");

        City city = new City();
        city.setCountry(new Country());

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(cityRepository.findById(userUpdate.getCityId())).thenReturn(Optional.of(city));
        when(cloudinaryService.uploadImage(img)).thenReturn("newPicturePath");

        userService.updateUser(userId, userUpdate);

        verify(userRepository).save(userEntity);
        assertEquals("newPicturePath", userUpdate.getProfilePicturePath());
    }

    @Test
    void shouldReturnNullWhenPrincipalIsNotCustomerUserDetails() {

        SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

        Long userId = UserServiceImpl.getLoggedUserId();

        assertNull(userId);
    }
    @Test
    public void testDeleteExpiredTokens() {

        LocalDateTime twentyFiveHoursAgo = LocalDateTime.now().minusHours(25);
        LocalDateTime twentyThreeHoursAgo = LocalDateTime.now().minusHours(23);


        userService.deleteExpiredTokens();


        verify(tokenRepository, times(1)).deleteVerificationTokenByExpirationTimeBefore(argThat(dateTime ->
                dateTime.isAfter(twentyFiveHoursAgo) && dateTime.isBefore(twentyThreeHoursAgo)));
    }

}
