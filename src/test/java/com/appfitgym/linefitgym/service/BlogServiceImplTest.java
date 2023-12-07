package com.appfitgym.linefitgym.service;

import com.appfitgym.model.dto.BlogCreateDto;
import com.appfitgym.model.dto.BlogViewDto;
import com.appfitgym.model.entities.Blog;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.UserRole;
import com.appfitgym.model.entities.country.City;
import com.appfitgym.model.entities.country.Country;
import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.repository.BlogRepository;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.service.CloudinaryService;
import com.appfitgym.service.impl.BlogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BlogServiceImplTest {

  @Mock private BlogRepository blogRepository;

  @Mock private UserRepository userRepository;

  @Mock private CloudinaryService cloudinaryService;

  @InjectMocks private BlogServiceImpl blogService;

  private Blog blog;
  private Blog blog2;

  private UserEntity userEntity;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    userEntity = mock(UserEntity.class);
    when(userEntity.getProfilePicture()).thenReturn("profilePicturePath");
    blog = new Blog();
    blog.setDate(LocalDate.now());
    blog.setUserEntity(userEntity);

    blog2 = new Blog();
    blog2.setDate(LocalDate.now());
    blog2.setUserEntity(userEntity);
  }

  @Test
  void testFindAllBlogs() {

    Pageable pageable = mock(Pageable.class);
    Page<Blog> blogs = new PageImpl<>(Arrays.asList(blog, blog2));

    when(blogRepository.findAll(pageable)).thenReturn(blogs);

    Page<BlogViewDto> result = blogService.findAllBlogs(pageable);

    assertEquals(blogs.getSize(), result.getSize());
  }

  @Test
  void testSearchBlogs() {

    Page<Blog> blogs = new PageImpl<>(Arrays.asList(blog, blog2));
    when(blogRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(blogs);

    Page<BlogViewDto> result = blogService.searchBlogs("query", "field", mock(Pageable.class));

    assertEquals(2, result.getTotalElements());
  }

  @Test
  void testFindBlogById() {

    when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blog));

    BlogViewDto result = blogService.findBlogById(1L);

    assertEquals(blog.getId(), result.getId());
    assertEquals(blog.getTitle(), result.getTitle());
  }

  @Test
  void testFindThreeOtherUserBlogs() {

    blog.setUserEntity(userEntity);
    when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blog));
    when(blogRepository.findAllByUserEntity(any(UserEntity.class)))
        .thenReturn(Arrays.asList(blog, blog, blog));

    List<BlogViewDto> result = blogService.findThreeOtherUserBlogs(1L);

    assertEquals(3, result.size());
  }

  @Test
  void testSaveBlog() throws IOException {

    BlogCreateDto blogCreateDto = new BlogCreateDto();
    blogCreateDto.setUserId(1L);
    blogCreateDto.setTitle("Title");
    blogCreateDto.setDescription("Description");
    blogCreateDto.setImage(mock(MultipartFile.class));

    Blog expectedBlog = new Blog();
    expectedBlog.setTitle("Title");

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
    when(cloudinaryService.uploadImage(any())).thenReturn("image-url");
    when(blogRepository.save(any(Blog.class))).thenReturn(expectedBlog);

    Blog result = blogService.saveBlog(blogCreateDto);

    assertEquals(expectedBlog.getTitle(), result.getTitle());
  }

  @Test
  void testDeleteBlog() {

    blogService.deleteBlog(1L);

    verify(blogRepository, times(1)).deleteById(1L);
  }
}
