package com.appfitgym.linefitgym.web;

import com.appfitgym.model.dto.UserRegistrationDto;
import com.appfitgym.model.dto.country.CountryLoadDto;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.mail.VerificationToken;
import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.repository.VerificationTokenRepository;
import com.appfitgym.service.CountryService;
import com.appfitgym.service.UserService;
import com.appfitgym.web.UserRegistrationController;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserRegistrationControllerTest {

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    @Mock
    private UserService userService;

    @Mock
    private CountryService countryService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private VerificationTokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnSexEnums() {
        SexEnum[] sexEnums = userRegistrationController.sexEnums();
        assertEquals(SexEnum.values().length, sexEnums.length);
    }

    @Test
    void shouldReturnRoles() {
        List<UserRoleEnum> roles = userRegistrationController.roles();
        assertEquals(UserRoleEnum.values().length - 1, roles.size());
        assertFalse(roles.contains(UserRoleEnum.ADMIN));
    }

    @Test
    void shouldReturnCountries() {
        List<CountryLoadDto> countries = new ArrayList<>();
        when(countryService.getAllCountries()).thenReturn(countries);
        assertEquals(countries, userRegistrationController.countries());
    }


    @Test
    void shouldRegisterUser() throws IOException {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);
        when(request.getContextPath()).thenReturn("/app");

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
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
                null
        );
        when(userService.register(any(UserRegistrationDto.class))).thenReturn(new UserEntity());
        ModelAndView modelAndView = userRegistrationController.register(userRegistrationDto, bindingResult, request);
        assertEquals("redirect:/users/login", modelAndView.getViewName());
    }

    @Test
    void shouldNotRegisterUserWhenBindingResultHasErrors() throws IOException {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
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
                null
        );
        when(bindingResult.hasErrors()).thenReturn(true);
        ModelAndView modelAndView = userRegistrationController.register(userRegistrationDto, bindingResult, null);
        assertEquals("register", modelAndView.getViewName());
    }

    @Test
    void shouldReturnRegisterView() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
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
                null 
        );
        ModelAndView modelAndView = userRegistrationController.register(userRegistrationDto, model);
        assertEquals("register", modelAndView.getViewName());
    }

    @Test
    void shouldRedirectToLoginWithMessageWhenUserIsActive() {
        VerificationToken theToken = mock(VerificationToken.class);
        UserEntity user = mock(UserEntity.class);
        when(user.isActive()).thenReturn(true);
        when(theToken.getUser()).thenReturn(user);
        when(tokenRepository.findByToken(anyString())).thenReturn(theToken);

        ModelAndView modelAndView = userRegistrationController.verifyEmail("token");
        assertEquals("redirect:/users/login", modelAndView.getViewName());
        assertEquals("This account has already been verified, please, login.", modelAndView.getModel().get("message"));
    }

    @Test
    void shouldRedirectToLoginWithSuccessMessageWhenTokenIsValid() {
        VerificationToken theToken = mock(VerificationToken.class);
        UserEntity user = mock(UserEntity.class);
        when(user.isActive()).thenReturn(false);
        when(theToken.getUser()).thenReturn(user);
        when(tokenRepository.findByToken(anyString())).thenReturn(theToken);
        when(userService.validateToken(anyString())).thenReturn("valid");

        ModelAndView modelAndView = userRegistrationController.verifyEmail("token");
        assertEquals("redirect:/users/login", modelAndView.getViewName());
        assertEquals("Email verified successfully. Now you can login to your account", modelAndView.getModel().get("message"));
    }

    @Test
    void shouldRedirectToLoginWithErrorMessageWhenTokenIsInvalid() {
        VerificationToken theToken = mock(VerificationToken.class);
        UserEntity user = mock(UserEntity.class);
        when(user.isActive()).thenReturn(false);
        when(theToken.getUser()).thenReturn(user);
        when(tokenRepository.findByToken(anyString())).thenReturn(theToken);
        when(userService.validateToken(anyString())).thenReturn("invalid");

        ModelAndView modelAndView = userRegistrationController.verifyEmail("token");
        assertEquals("redirect:/users/login", modelAndView.getViewName());
        assertEquals("Invalid verification token", modelAndView.getModel().get("message"));
    }
}