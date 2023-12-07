package com.appfitgym.sheduler;

import com.appfitgym.service.UserService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class VerificationTokenCleanUpScheduler {

    private final UserService userService;

    public VerificationTokenCleanUpScheduler(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 */24 * * *")
    public void deleteExpiredTokens() {
        userService.deleteExpiredTokens();
    }
}