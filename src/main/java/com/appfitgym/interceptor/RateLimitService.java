package com.appfitgym.interceptor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class RateLimitService {

    private static final int MAX_ATTEMPTS = 5;
    private static final int BLOCK_DURATION_MINUTES = 15;
    private Map<String, ConcurrentLinkedDeque<LocalDateTime>> attempts = new ConcurrentHashMap<>();

    public void incrementAttempts(String username) {
        ConcurrentLinkedDeque<LocalDateTime> userAttempts = attempts.computeIfAbsent(username, k -> new ConcurrentLinkedDeque<>());
        userAttempts.add(LocalDateTime.now());

        userAttempts.removeIf(attemptTime -> ChronoUnit.MINUTES.between(attemptTime, LocalDateTime.now()) > BLOCK_DURATION_MINUTES);

        while (userAttempts.size() > MAX_ATTEMPTS) {
            userAttempts.poll();
        }

    }

    public boolean isBlocked(String username) {
        ConcurrentLinkedDeque<LocalDateTime> userAttempts = attempts.get(username);
        return userAttempts != null && userAttempts.size() >= MAX_ATTEMPTS;
    }
}