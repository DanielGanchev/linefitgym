package com.appfitgym.config;

import com.appfitgym.repository.UserRepository;
import com.appfitgym.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }
}
