package com.appfitgym.repository;

import com.appfitgym.model.entities.UserRole;
import com.appfitgym.model.enums.UserRoleEnum;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("SELECT ur FROM UserRole ur join UserEntity u on ur.id = u.id where u.id = :id")
    List<UserRole> findAllByUserId(@Param("id") Long id);


    @Query("SELECT ur FROM UserRole ur where ur.role = :role")
    Optional<UserRole> findByRole(@RequestParam("role") UserRoleEnum role);
}
