package com.appfitgym.service.impl;


import com.appfitgym.config.UserSpecification;
import com.appfitgym.model.dto.GalleryUserDetailsDto;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.UserRole;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.service.AllUserGalleryService;
import jakarta.persistence.criteria.Join;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AllUsersGalleryService implements AllUserGalleryService {

    private final UserRepository userRepository;


    public AllUsersGalleryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Page<GalleryUserDetailsDto> findAllUsers(Pageable pageable) {
        return userRepository.findAllWithoutAdminOnlyActiveStatus(pageable).map(AllUsersGalleryService::mapAsGalleryUserDetailsDto);
    }

    private static GalleryUserDetailsDto mapAsGalleryUserDetailsDto(UserEntity userEntity) {

        return new GalleryUserDetailsDto()
                .setId(userEntity.getId())
                .setRole(userEntity.getRoles().get(0).getRole().name())
                .setFullName(userEntity.getFirstName() + " " + userEntity.getLastName())
                .setEmail(userEntity.getEmail())
                .setCountry(userEntity.getCountry().getName())
                .setCity(userEntity.getCity().getName())
                .setAge(ageOfUser(userEntity))
                .setProfilePictureUrl(userEntity.getProfilePicture());

    }

    private static int ageOfUser(UserEntity userEntity) {
        LocalDate birthDate = userEntity.getBirthDate();
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDate, currentDate).getYears();
    }

    @Override
    public Page<GalleryUserDetailsDto> searchUsersInGallery(String query, String field, Pageable pageable) {
        Specification<UserEntity> spec = Specification.where(new UserSpecification(field, query))
                .and((root, criteriaQuery, criteriaBuilder) -> {
                    Join<UserEntity, UserRole> rolesJoin = root.join("roles");
                    return criteriaBuilder.notEqual(rolesJoin.get("role"), UserRoleEnum.ADMIN);
                });

        return userRepository.findAll(spec, pageable).map(AllUsersGalleryService::mapAsGalleryUserDetailsDto);
    }

    @Override
    public List<GalleryUserDetailsDto> findTopCoaches() {
        return userRepository.findTopCoaches().stream().map(AllUsersGalleryService::mapAsGalleryUserDetailsDto).collect(Collectors.toList());
    }



}
