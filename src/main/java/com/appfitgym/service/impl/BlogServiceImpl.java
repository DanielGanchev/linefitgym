package com.appfitgym.service.impl;

import com.appfitgym.config.BlogSpecifications;
import com.appfitgym.model.dto.BlogViewDto;
import com.appfitgym.model.entities.Blog;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.repository.BlogRepository;
import com.appfitgym.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public static BlogViewDto mapBlogToBlogViewDto(Blog blog) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new BlogViewDto()
                .setId(blog.getId())
                .setDescription(blog.getDescription())
                .setAuthor(blog.getAuthor())
                .setTitle(blog.getTitle())
                .setDate(blog.getDate().format(formatter))
                .setImage(blog.getImage())
                .setAuthorImage(blog.getUserEntity().getProfilePicture())
                .setAuthorId(blog.getUserEntity().getId());

    }

    @Override

    public Page<BlogViewDto> searchBlogs(String query, String field, Pageable pageable) {
        Specification<Blog> specification = Specification.where(new BlogSpecifications(field, query));

        return blogRepository.findAll(specification, pageable).map(BlogServiceImpl::mapBlogToBlogViewDto);
    }

    @Override
    public BlogViewDto findBlogById(Long id) {
        return blogRepository.findById(id).map(BlogServiceImpl::mapBlogToBlogViewDto).orElseThrow();
    }

    @Override
    public List<BlogViewDto> findThreeOtherUserBlogs(Long id) {

        UserEntity userEntity = blogRepository.findById(id).orElseThrow().getUserEntity();
        return blogRepository.findAllByUserEntity(userEntity).stream().map(BlogServiceImpl::mapBlogToBlogViewDto).limit(3).toList();
    }


}
