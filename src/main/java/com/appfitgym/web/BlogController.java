package com.appfitgym.web;

import com.appfitgym.model.dto.BlogViewDto;
import com.appfitgym.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }


    @GetMapping("")
    public ModelAndView blog(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size, Model model){
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<BlogViewDto>  blogs = blogService.findAllBlogs(pageable);
        model.addAttribute("blogs", blogs);


        return new ModelAndView("blog");
    }




}
