package com.appfitgym.repository;

import com.appfitgym.model.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("SELECT ur FROM UserRole ur join User u on ur.id = u.id where u.id = :id")
    List<UserRole> findAllByUserId(@Param("id") Long id);
}
