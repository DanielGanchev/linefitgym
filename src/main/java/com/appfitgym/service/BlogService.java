package com.appfitgym.service;

import com.appfitgym.model.dto.BlogViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Page<BlogViewDto> findAllBlogs(Pageable pageable);
}
