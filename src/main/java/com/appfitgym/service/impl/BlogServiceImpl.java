package com.appfitgym.service.impl;

import com.appfitgym.model.dto.BlogViewDto;
import com.appfitgym.model.entities.Blog;
import com.appfitgym.repository.BlogRepository;
import com.appfitgym.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class BlogServiceImpl  implements BlogService {


    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }


    @Override
    public Page<BlogViewDto> findAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable).map(BlogServiceImpl::mapBlogToBlogViewDto);
    }

    private static BlogViewDto mapBlogToBlogViewDto(Blog blog) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new BlogViewDto()
                .setId(blog.getId())
                .setDescription(blog.getDescription())
                .setAuthor(blog.getAuthor())
                .setTitle(blog.getTitle())
                .setDate(blog.getDate().format(formatter))
                .setImage(blog.getImage());

    }
}
