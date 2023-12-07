package com.appfitgym.linefitgym.sheduler;

import com.appfitgym.service.UserService;
import com.appfitgym.sheduler.VerificationTokenCleanUpScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class VerificationTokenCleanUpSchedulerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private VerificationTokenCleanUpScheduler scheduler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteExpiredTokens() {
        scheduler.deleteExpiredTokens();
        verify(userService, times(1)).deleteExpiredTokens();
    }
}