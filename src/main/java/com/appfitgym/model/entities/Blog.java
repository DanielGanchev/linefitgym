package com.appfitgym.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "blogs")
public class Blog extends BaseEntity {


    private String title;
    private String description;
    private String author;

    private LocalDate date;
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public Blog setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Blog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Blog setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Blog setAuthor(String author) {
        this.author = author;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Blog setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Blog setImage(String image) {
        this.image = image;
        return this;
    }
}
