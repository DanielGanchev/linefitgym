package com.appfitgym.web;

import com.appfitgym.model.dto.UserDetailsAdminPage;
import com.appfitgym.service.AdminService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("")
  public ModelAndView admin() {
    return new ModelAndView("admin-table");
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/all")
  public String all(Model model, @PageableDefault(size = 9, sort = "username") Pageable pageable) {

    Page<UserDetailsAdminPage> allUsers = adminService.findAllUsers(pageable);

    model.addAttribute("users", allUsers);

    return "admin-table";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/search")
  public String search(
      Model model,
      @RequestParam String query,
      @RequestParam String field,
      @PageableDefault(size = 4, sort = "username") Pageable pageable) {
    List<UserDetailsAdminPage> userList =
        adminService.searchUsers(query, field, pageable).stream()
            .filter(x -> !x.getRole().equals("ADMIN"))
            .collect(Collectors.toList());
    Page<UserDetailsAdminPage> allOffers = new PageImpl<>(userList, pageable, userList.size());

    model.addAttribute("users", allOffers);

    return "admin-table";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/delete")
  public String deleteUser(@RequestParam Long userId) {
    adminService.deleteUser(userId);
    return "redirect:/admin/all";
  }
}
