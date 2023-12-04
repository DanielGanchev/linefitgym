package com.appfitgym.repository;

import com.appfitgym.model.entities.Blog;


import com.appfitgym.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;


@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {


    List<Blog> findAllByUserEntity(UserEntity userEntity);
}
