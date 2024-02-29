package com.appfitgym.linefitgym.web;

import com.appfitgym.model.dto.UserUpdateValidationDto;
import com.appfitgym.model.dto.country.CountryLoadDto;
import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.service.CountryService;
import com.appfitgym.service.UserService;
import com.appfitgym.web.UserUpdateController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserUpdateControllerTest {

    @InjectMocks
    private UserUpdateController userUpdateController;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateUserDetails() {
        UserUpdateValidationDto userUpdate = new UserUpdateValidationDto();
        userUpdate.setId(1L);
        when(userService.getUserDetails(anyLong())).thenReturn(Optional.of(userUpdate));
        userUpdateController.updateUser(1L, model);
        verify(userService, times(1)).getUserDetails(anyLong());
    }

    @Test
    void shouldNotUpdateUserDetailsWhenUserNotFound() {
        when(userService.getUserDetails(anyLong())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userUpdateController.updateUser(1L, model));
    }

    @Test
    void shouldUpdateUser() throws IOException {
        UserUpdateValidationDto userUpdate = new UserUpdateValidationDto();
        userUpdate.setId(1L);
        when(userService.getUserDetails(anyLong())).thenReturn(Optional.of(userUpdate));
        when(bindingResult.hasErrors()).thenReturn(false);
        userUpdateController.updateUser(1L, userUpdate, bindingResult, redirectAttributes);
        verify(userService, times(1)).updateUser(anyLong(), any(UserUpdateValidationDto.class));
    }

    @Test
    void shouldNotUpdateUserWhenBindingResultHasErrors() throws IOException {
        UserUpdateValidationDto userUpdate = new UserUpdateValidationDto();
        userUpdate.setId(1L);
        when(userService.getUserDetails(anyLong())).thenReturn(Optional.of(userUpdate));
        when(bindingResult.hasErrors()).thenReturn(true);
        userUpdateController.updateUser(1L, userUpdate, bindingResult, redirectAttributes);
        verify(userService, times(0)).updateUser(anyLong(), any(UserUpdateValidationDto.class));
    }

    @Test
    void shouldReturnSexEnums() {
        SexEnum[] sexEnums = userUpdateController.sexEnums();
        assertEquals(SexEnum.values().length, sexEnums.length);
    }

    @Test
    void shouldReturnRoles() {
        List<UserRoleEnum> roles = userUpdateController.roles();
        assertEquals(UserRoleEnum.values().length - 1, roles.size());
        assertFalse(roles.contains(UserRoleEnum.ADMIN));
    }

    @Test
    void shouldReturnCountries() {
        List<CountryLoadDto> countries = new ArrayList<>();
        when(countryService.getAllCountries()).thenReturn(countries);
        assertEquals(countries, userUpdateController.countries());
    }
}