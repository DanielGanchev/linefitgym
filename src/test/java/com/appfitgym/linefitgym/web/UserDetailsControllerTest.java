package com.appfitgym.linefitgym.web;

import com.appfitgym.model.dto.UserDetailsViewDto;
import com.appfitgym.service.UserService;
import com.appfitgym.web.UserDetailsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserDetailsControllerTest {

    @InjectMocks
    private UserDetailsController userDetailsController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnUserDetailsView() {
        UserDetailsViewDto userDetailsViewDto = new UserDetailsViewDto();
        when(userService.getUserViewDetails(1L)).thenReturn(userDetailsViewDto);

        ModelAndView modelAndView = userDetailsController.details(1L);

        assertEquals("user-details-view", modelAndView.getViewName());
        assertEquals(userDetailsViewDto, modelAndView.getModel().get("user"));
    }
}