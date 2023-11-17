package com.appfitgym.service.impl;


import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.UserRole;
import com.appfitgym.repository.UserRepository;




import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;





@Service
public class UserDetailsServiceImpl implements UserDetailsService {

        private final UserRepository userRepository;



        public UserDetailsServiceImpl(UserRepository userRepository) {
            this.userRepository = userRepository;

        }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user by username: " + username);
        return userRepository
                .findByUsername(username)
                .map(userEntity -> {
                    System.out.println("User found: " + userEntity.getUsername());
                    return map(userEntity);
                })
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
    }

    private static UserDetails map(UserEntity userEntity) {
        System.out.println("Mapping user: " + userEntity.getUsername());
        return User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream().map(UserDetailsServiceImpl::map).toList())
                .build();
    }

    private static GrantedAuthority map(UserRole role) {
        System.out.println("Mapping role: " + role.getRole().name());
        return new SimpleGrantedAuthority("ROLE_" + role.getRole().name());
    }



    }
