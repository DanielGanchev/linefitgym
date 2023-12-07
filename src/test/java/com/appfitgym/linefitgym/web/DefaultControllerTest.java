package com.appfitgym.linefitgym.web;

import com.appfitgym.web.DefaultController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultControllerTest {

    private DefaultController defaultController;

    @BeforeEach
    void setUp() {
        defaultController = new DefaultController();
    }

    @Test
    void shouldReturnIndexView() {
        String view = defaultController.index();
        assertEquals("index", view);
    }
}