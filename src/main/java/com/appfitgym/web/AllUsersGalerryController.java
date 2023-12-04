package com.appfitgym.web;

import com.appfitgym.model.dto.GalleryUserDetailsDto;
import com.appfitgym.service.impl.AllUsersGalleryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/findUsers")
public class AllUsersGalerryController {

    private final AllUsersGalleryService allUsersGalleryService;

    public AllUsersGalerryController(AllUsersGalleryService allUsersGalleryService) {
        this.allUsersGalleryService = allUsersGalleryService;
    }

    @ModelAttribute()

    @GetMapping("")
    public ModelAndView findAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size, Model model) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<GalleryUserDetailsDto> users = allUsersGalleryService.findAllUsers(pageable);
        model.addAttribute("users", users);

        List<GalleryUserDetailsDto> usersList = allUsersGalleryService.findTopCoaches();
        model.addAttribute("topCoaches", usersList);


        return new ModelAndView("gallery-users");
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam String query, @RequestParam String field, @PageableDefault(size = 8, sort = "username") Pageable pageable) {
        Pageable pageable1 = Pageable.ofSize(8).withPage(0);
        Page<GalleryUserDetailsDto> userList = allUsersGalleryService.searchUsersInGallery(query, field, pageable1);


        model.addAttribute("users", userList);
        List<GalleryUserDetailsDto> usersList = allUsersGalleryService.findTopCoaches();
        model.addAttribute("topCoaches", usersList);
        return "gallery-users";
    }







}
