package com.appfitgym.linefitgym.service;

import com.appfitgym.interceptor.RateLimitService;
import com.appfitgym.model.dto.CustomerUserDetails;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private RateLimitService rateLimitService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowIllegalArgumentException() {
        String username = "username";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);


        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        assertThrows(IllegalArgumentException.class, () -> userDetailsService.loadUserByUsername(username));
    }

    @Test
    void shouldThrowUsernameNotFoundException() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("username"));
    }

    @Test
    void shouldReturnCustomerUserDetails() {
        String username = "username";


        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername(username);
        userEntity.setPassword("password");
        userEntity.setActive(true);
        userEntity.setRoles(new ArrayList<>());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
    }
}