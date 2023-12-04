package com.appfitgym.web;

import com.appfitgym.model.dto.BlogViewDto;
import com.appfitgym.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }


    @GetMapping("")
    public ModelAndView blog(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size, Model model){
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<BlogViewDto>  blogs = blogService.findAllBlogs(pageable);
        model.addAttribute("blogs", blogs);


        return new ModelAndView("blog");
    }

    @GetMapping("/search")
    public ModelAndView search(Model model,@RequestParam String query, @RequestParam String field ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size ){
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<BlogViewDto>  blogs = blogService.searchBlogs(query, field, pageable);
        model.addAttribute("blogs", blogs);

        return new ModelAndView("blog");
        }

    @GetMapping("/single/{id}")
    public ModelAndView singleBlog(@PathVariable Long id, Model model){
        BlogViewDto blog = blogService.findBlogById(id);
        List<BlogViewDto> otherThreeUserBlogs = blogService.findThreeOtherUserBlogs(id);
        model.addAttribute("blog", blog);
        model.addAttribute("otherUsersBlogs", otherThreeUserBlogs);
        return new ModelAndView("single-post");
    }

    @PreAuthorize("#userId == principal.getId()")
    @GetMapping("/create/{userId}")
    public ModelAndView createBlog(@PathVariable("userId") Long userId, Model model){
        model.addAttribute("userId", userId);


        return new ModelAndView("create");
    }





}
