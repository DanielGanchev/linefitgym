package com.appfitgym.util;

import com.appfitgym.model.entities.UserRole;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    public DatabaseInitializer(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) {
        if (userRoleRepository.count() > 0) {
            return;
        }

        for (UserRoleEnum roleEnum : UserRoleEnum.values()) {
                UserRole userRole = new UserRole();
                userRole.setRole(roleEnum);
                userRoleRepository.save(userRole);

        }
    }
}