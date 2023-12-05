package com.appfitgym.service.impl;

import com.appfitgym.config.BlogSpecifications;
import com.appfitgym.model.dto.BlogCreateDto;
import com.appfitgym.model.dto.BlogViewDto;
import com.appfitgym.model.entities.Blog;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.repository.BlogRepository;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.service.BlogService;
import com.appfitgym.service.CloudinaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BlogServiceImpl  implements BlogService {


    private final BlogRepository blogRepository;

    private final UserRepository userRepository;

    private final CloudinaryService cloudinaryService;

    public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository, CloudinaryService cloudinaryService) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
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

    @Override
    public Blog saveBlog(BlogCreateDto blogViewDto) throws IOException {
        return blogRepository.save(mapBlogViewDtoToBlog(blogViewDto));


    }

    private Blog mapBlogViewDtoToBlog(BlogCreateDto blogViewDto) throws IOException {


        UserEntity userEntity = userRepository.findById(blogViewDto.getUserId()).orElseThrow();
        MultipartFile img =blogViewDto.getImage();
        String url = cloudinaryService.uploadImage(img);

        return new Blog()
                .setTitle(blogViewDto.getTitle())
                .setDescription(blogViewDto.getDescription())
                .setImage(url)
                .setDate(LocalDate.now())
                .setUserEntity(userEntity)
                .setAuthor(userEntity.getFirstName() + " " + userEntity.getLastName());
    }


}
