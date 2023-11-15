package com.appfitgym.service.impl;

import com.appfitgym.model.entities.LineFitGymUserDetails;
import com.appfitgym.model.entities.UserRole;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.model.entities.User;

import com.appfitgym.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
    @Transactional
    public class UserDetailsServiceImpl implements UserDetailsService {

        private final UserRepository userRepository;

        private final UserRoleRepository userRoleRepository;

        public UserDetailsServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
            this.userRepository = userRepository;
            this.userRoleRepository = userRoleRepository;
        }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findByEmail(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));

    }


        private LineFitGymUserDetails map(User userEntity) {
            return new LineFitGymUserDetails(

                    userEntity.getId(),
                    userEntity.getEmail(),
                    userEntity.getPassword(),
                    userEntity.getFirstName(),
                    userEntity.getLastName(),
                    userEntity.isEnabled(),
                    userEntity.getRoles().stream().map(this::map).toList()
            );



        }

    private GrantedAuthority map(UserRole userRoleEntity) {

        return new SimpleGrantedAuthority("ROLE_" + userRoleEntity.getRole().name());
    }



    }
