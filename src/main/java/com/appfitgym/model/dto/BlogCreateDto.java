package com.appfitgym.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import validation.ValidFile;

public class BlogCreateDto {

    @NotNull
    @NotEmpty(message = "Title cannot be empty!")
    @Size(min = 3, max = 100, message = "Title length must be between 3 and 20 characters!")
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @ValidFile
    private MultipartFile image;

    public Long getUserId() {
        return userId;
    }

    public BlogCreateDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    private Long userId;

    public String getTitle() {
        return title;
    }

    public BlogCreateDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BlogCreateDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public BlogCreateDto setImage(MultipartFile image) {
        this.image = image;
        return this;
    }
}
