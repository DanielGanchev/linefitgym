package com.appfitgym.linefitgym.web;

import com.appfitgym.model.dto.CustomerUserDetails;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.service.UserService;
import com.appfitgym.web.GlobalControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static com.appfitgym.service.impl.UserServiceImpl.getLoggedUserId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class GlobalControllerAdviceTest {

    @InjectMocks
    private GlobalControllerAdvice globalControllerAdvice;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("user");
        userEntity.setPassword("password");
        userEntity.setActive(true);
        userEntity.setRoles(new ArrayList<>());
        UserDetails principal = new CustomerUserDetails(userEntity);


        Authentication auth = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());


        SecurityContextHolder.getContext().setAuthentication(auth);
    }

  @Test
    void shouldReturnCorrectUserIdWhenUserIsAuthenticated() {
        Long expectedUserId = 1L;
        when(userService.getUserViewDetails(expectedUserId)).thenReturn(null);

        Long actualUserId = globalControllerAdvice.getUserId();

        assertEquals(expectedUserId, actualUserId);
    }


}