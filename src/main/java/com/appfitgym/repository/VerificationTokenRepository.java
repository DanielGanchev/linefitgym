package com.appfitgym.repository;

import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.mail.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    Optional<Object> findByUser(UserEntity userEntity);

    void deleteVerificationTokenByExpirationTimeBefore(LocalDateTime localDateTime);


}