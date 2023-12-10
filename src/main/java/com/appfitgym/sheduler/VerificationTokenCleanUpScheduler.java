package com.appfitgym.sheduler;

import com.appfitgym.service.UserService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component

public class VerificationTokenCleanUpScheduler {

    private final UserService userService;

    public VerificationTokenCleanUpScheduler(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void deleteExpiredTokens() {
        userService.deleteExpiredTokens();
    }
}