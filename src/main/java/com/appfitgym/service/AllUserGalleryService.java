package com.appfitgym.service;

import com.appfitgym.model.dto.GalleryUserDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AllUserGalleryService  {


    Page<GalleryUserDetailsDto> findAllUsers(Pageable pageable);



    Page<GalleryUserDetailsDto> searchUsersInGallery(String query, String field, Pageable pageable);

    List<GalleryUserDetailsDto> findTopCoaches();
}
