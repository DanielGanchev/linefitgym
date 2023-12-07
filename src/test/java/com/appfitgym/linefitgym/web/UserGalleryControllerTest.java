package com.appfitgym.linefitgym.web;

import com.appfitgym.model.dto.RandomUserDto;
import com.appfitgym.service.GalleryService;
import com.appfitgym.web.UserGalleryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserGalleryControllerTest {

    @InjectMocks
    private UserGalleryController userGalleryController;

    @Mock
    private GalleryService galleryService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnGalleryViewWithRandomUserAndUsers() {
        RandomUserDto randomUserDto = new RandomUserDto();
        List<RandomUserDto> randomUsers = Arrays.asList(new RandomUserDto(), new RandomUserDto());

        when(galleryService.getRandomUser()).thenReturn(randomUserDto);
        when(galleryService.getRandomUsers()).thenReturn(randomUsers);

        ModelAndView modelAndView = userGalleryController.gallery(model);

        assertEquals("details", modelAndView.getViewName());
    }
}