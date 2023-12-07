package com.appfitgym.linefitgym.interceptor;

import com.appfitgym.interceptor.RateLimitService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RateLimitServiceTest {

    private RateLimitService rateLimitService = new RateLimitService();

    @Test
    public void testIncrementAttempts() {
        String username = "testUser";


        for (int i = 0; i < 4; i++) {
            rateLimitService.incrementAttempts(username);
        }


        assertFalse(rateLimitService.isBlocked(username));


        rateLimitService.incrementAttempts(username);


        assertTrue(rateLimitService.isBlocked(username));
    }

    @Test
    public void testIsBlocked() {
        String username = "testUser";


        assertFalse(rateLimitService.isBlocked(username));


        for (int i = 0; i < 6; i++) {
            rateLimitService.incrementAttempts(username);
        }

        // The user should now be blocked
        assertTrue(rateLimitService.isBlocked(username));
    }
}