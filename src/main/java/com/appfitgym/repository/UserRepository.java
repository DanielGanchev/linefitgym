package com.appfitgym.repository;

import com.appfitgym.model.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN u.roles r WHERE r.role != 'ADMIN' ORDER BY RAND() limit 1")
    UserEntity getRandomUser();

    @Query("SELECT distinct u FROM UserEntity u LEFT JOIN u.roles r WHERE r.role != 'ADMIN' ORDER BY RAND() limit 6")
    List<UserEntity> getRandomUsers();
}
