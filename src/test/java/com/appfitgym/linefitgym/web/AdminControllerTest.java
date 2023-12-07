package com.appfitgym.linefitgym.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.appfitgym.model.dto.UserDetailsAdminPage;
import com.appfitgym.service.AdminService;
import com.appfitgym.web.AdminController;
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

public class AdminControllerTest {

  @InjectMocks private AdminController adminController;

  @Mock private AdminService adminService;

  @Mock private Model model;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnAllUsers() {
    Pageable pageable = Pageable.unpaged();
    List<UserDetailsAdminPage> usersList =
        Arrays.asList(new UserDetailsAdminPage(), new UserDetailsAdminPage());
    Page<UserDetailsAdminPage> usersPage = new PageImpl<>(usersList);
    when(adminService.findAllUsers(pageable)).thenReturn(usersPage);

    String view = adminController.all(model, pageable);

    assertEquals("admin-table", view);
  }

  @Test
  void shouldSearchUsers() {
    String query = "query";
    String field = "field";
    Pageable pageable = Pageable.unpaged();

    UserDetailsAdminPage userDetailsAdminPage = new UserDetailsAdminPage();
    userDetailsAdminPage.setRole("COACH");

    List<UserDetailsAdminPage> usersList = Arrays.asList(userDetailsAdminPage);
    Page<UserDetailsAdminPage> usersPage = new PageImpl<>(usersList);

    when(adminService.searchUsers(query, field, pageable)).thenReturn(usersPage);

    String view = adminController.search(model, query, field, pageable);

    assertEquals("admin-table", view);
  }

  @Test
  void shouldDeleteUser() {
    Long userId = 1L;

    String view = adminController.deleteUser(userId);

    verify(adminService).deleteUser(userId);
    assertEquals("redirect:/admin/all", view);
  }

  @Test
  void shouldReturnAdminView() {
    ModelAndView view = adminController.admin();

    assertEquals("admin-table", view.getViewName());
  }
}
