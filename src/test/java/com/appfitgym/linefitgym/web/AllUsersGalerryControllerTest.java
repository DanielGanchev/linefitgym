package com.appfitgym.linefitgym.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.appfitgym.model.dto.GalleryUserDetailsDto;
import com.appfitgym.service.impl.AllUsersGalleryService;
import com.appfitgym.web.AllUsersGalerryController;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public class AllUsersGalerryControllerTest {

  @InjectMocks private AllUsersGalerryController allUsersGalerryController;

  @Mock private AllUsersGalleryService allUsersGalleryService;

  @Mock private Model model;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldFindAllUsers() {
    List<GalleryUserDetailsDto> usersList =
        Arrays.asList(new GalleryUserDetailsDto(), new GalleryUserDetailsDto());
    Page<GalleryUserDetailsDto> usersPage = new PageImpl<>(usersList);
    when(allUsersGalleryService.findAllUsers(Pageable.unpaged())).thenReturn(usersPage);

    List<GalleryUserDetailsDto> topCoachesList =
        Arrays.asList(new GalleryUserDetailsDto(), new GalleryUserDetailsDto());
    when(allUsersGalleryService.findTopCoaches()).thenReturn(topCoachesList);

    ModelAndView view = allUsersGalerryController.findAllUsers(0, 8, model);

    assertEquals("gallery-users", view.getViewName());
  }

  @Test
  void shouldSearchUsers() {
    String query = "query";
    String field = "field";
    Pageable pageable = Pageable.ofSize(8).withPage(0);
    List<GalleryUserDetailsDto> usersList =
        Arrays.asList(new GalleryUserDetailsDto(), new GalleryUserDetailsDto());
    Page<GalleryUserDetailsDto> usersPage = new PageImpl<>(usersList);
    when(allUsersGalleryService.searchUsersInGallery(query, field, pageable)).thenReturn(usersPage);

    List<GalleryUserDetailsDto> topCoachesList =
        Arrays.asList(new GalleryUserDetailsDto(), new GalleryUserDetailsDto());
    when(allUsersGalleryService.findTopCoaches()).thenReturn(topCoachesList);

    String view = allUsersGalerryController.search(model, query, field, pageable);

    assertEquals("gallery-users", view);
  }
}
