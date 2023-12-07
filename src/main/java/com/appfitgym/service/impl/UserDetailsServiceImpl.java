package com.appfitgym.service.impl;

import com.appfitgym.interceptor.RateLimitService;
import com.appfitgym.model.dto.CustomerUserDetails;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.repository.UserRepository;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  private final RateLimitService rateLimitService;

  public UserDetailsServiceImpl(UserRepository userRepository, RateLimitService rateLimitService) {
    this.userRepository = userRepository;
    this.rateLimitService = rateLimitService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));

    if (rateLimitService.isBlocked(username)) {
      throw new LockedException("User is blocked");
    }
    return new CustomerUserDetails(user);
  }
}
