package com.appfitgym.service;

import com.appfitgym.model.dto.UserDetailsAdminPage;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    Page<UserDetailsAdminPage> findAllUsers(Pageable pageable);

    Page<UserDetailsAdminPage> searchUsers(String query, String field, Pageable pageable);


    @Transactional
    void deleteUser(Long userId);
}
