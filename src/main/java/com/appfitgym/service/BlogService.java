package com.appfitgym.service;

import com.appfitgym.model.dto.BlogViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    Page<BlogViewDto> findAllBlogs(Pageable pageable);



    Page<BlogViewDto> searchBlogs(String query, String field, Pageable pageable);

    BlogViewDto findBlogById(Long id);

    List<BlogViewDto> findThreeOtherUserBlogs(Long id);
}
