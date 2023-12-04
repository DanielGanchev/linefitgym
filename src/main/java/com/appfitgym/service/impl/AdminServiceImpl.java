package com.appfitgym.service.impl;

import com.appfitgym.config.UserSpecification;
import com.appfitgym.model.dto.UserDetailsAdminPage;
import com.appfitgym.model.entities.UserEntity;

import com.appfitgym.repository.*;
import com.appfitgym.service.AdminService;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;
    private final CityRepository cityRepository;

    private final CountryRepository countryRepository;

    private static VerificationTokenRepository verificationTokenRepository;


public AdminServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, CityRepository cityRepository, CountryRepository countryRepository, VerificationTokenRepository verificationTokenRepository) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.cityRepository = cityRepository;
    this.countryRepository = countryRepository;
    this.verificationTokenRepository = verificationTokenRepository;

}

    @Override
    public Page<UserDetailsAdminPage> findAllUsers(Pageable pageable) {
           return userRepository.findAllWithoutAdminEveryStatus(pageable).map(AdminServiceImpl::mapAsUserDetailsAdminPage);
    }

    private static UserDetailsAdminPage mapAsUserDetailsAdminPage(UserEntity userEntity) {
      return new UserDetailsAdminPage()
              .setId(userEntity.getId())
              .setUsername(userEntity.getUsername())
              .setFirstName(userEntity.getFirstName())
              .setLastName(userEntity.getLastName())
              .setEmail(userEntity.getEmail())
              .setCountry(userEntity.getCountry().getName())
              .setCity(userEntity.getCity().getName())
              .setRole(userEntity.getRoles().get(0).getRole().name())
              .setCreatedOn(userEntity.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))

              .setAge(ageOfUser(userEntity))
              .setStatus(statusOfUser(userEntity,verificationTokenRepository));

    }

    private static String statusOfUser(UserEntity userEntity,VerificationTokenRepository verificationTokenRepository) {
        if (userEntity.isActive()) {
            return "approved";


        } else if (verificationTokenRepository.findByUser(userEntity).isPresent()) {
            return "pending";
        } else {
            return "decline";
        }
    }

    private static int ageOfUser(UserEntity userEntity) {
        LocalDate birthDate = userEntity.getBirthDate();
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDate, currentDate).getYears();
    }

  @Override
  public Page<UserDetailsAdminPage> searchUsers(String query, String field, Pageable pageable) {
    Specification<UserEntity> spec = new UserSpecification(field, query);
    return userRepository.findAll(spec, pageable).map(AdminServiceImpl::mapAsUserDetailsAdminPage);
  }

   @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }



}
