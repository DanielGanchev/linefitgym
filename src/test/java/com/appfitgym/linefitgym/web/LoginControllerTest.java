package com.appfitgym.linefitgym.web;

import com.appfitgym.interceptor.RateLimitService;
import com.appfitgym.web.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private RateLimitService rateLimitService;

    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        model = mock(Model.class);
    }

    @Test
    void shouldReturnLoginView() {
        String view = loginController.login();
        assertEquals("login", view);
    }

    @Test
    void shouldReturnLoginViewWithErrorMessage() {
        String username = "testUser";
        when(model.addAttribute("username", username)).thenReturn(model);
        when(model.addAttribute("bad_credentials", true)).thenReturn(model);

        String view = loginController.failedLogin(username, model);

        assertEquals("login", view);
    }
}