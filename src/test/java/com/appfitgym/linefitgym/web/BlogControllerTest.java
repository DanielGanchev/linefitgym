package com.appfitgym.linefitgym.web;

import com.appfitgym.model.dto.BlogCreateDto;
import com.appfitgym.model.dto.BlogViewDto;
import com.appfitgym.service.BlogService;
import com.appfitgym.web.BlogController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.io.IOException;

import static org.mockito.Mockito.*;

class BlogControllerTest {

    @InjectMocks
    private BlogController blogController;

    @Mock
    private BlogService blogService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllBlogs() {
        blogController.blog(0, 6, model);
        verify(blogService, times(1)).findAllBlogs(any(Pageable.class));
    }

    @Test
    void shouldSearchBlogs() {
        blogController.search(model, "query", "field", 0, 6);
        verify(blogService, times(1)).searchBlogs(anyString(), anyString(), any(Pageable.class));
    }

    @Test
    void shouldGetSingleBlog() {
        blogController.singleBlog(1L, model);
        verify(blogService, times(1)).findBlogById(anyLong());
        verify(blogService, times(1)).findThreeOtherUserBlogs(anyLong());
    }

    @Test
    void shouldCreateBlog() {
        blogController.createBlog(1L, new BlogCreateDto(), model);
        verify(model, times(1)).addAttribute(anyString(), anyLong());
    }

    @Test
    void shouldSaveBlog() throws IOException {
        when(bindingResult.hasErrors()).thenReturn(false);
        blogController.saveBlog(1L, new BlogCreateDto(), bindingResult);
        verify(blogService, times(1)).saveBlog(any(BlogCreateDto.class));
    }

    @Test
    void shouldNotSaveBlogWhenBindingResultHasErrors() throws IOException {
        when(bindingResult.hasErrors()).thenReturn(true);
        blogController.saveBlog(1L, new BlogCreateDto(), bindingResult);
        verify(blogService, times(0)).saveBlog(any(BlogCreateDto.class));
    }

    @Test
    void shouldDeleteBlog() {
        blogController.deleteBlog(1L);
        verify(blogService, times(1)).deleteBlog(anyLong());
    }
}