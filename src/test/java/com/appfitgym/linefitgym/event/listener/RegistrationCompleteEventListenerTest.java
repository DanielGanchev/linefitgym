package com.appfitgym.linefitgym.event.listener;

import com.appfitgym.event.RegistrationCompleteEvent;
import com.appfitgym.event.listener.RegistrationCompleteEventListener;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;


import static org.mockito.Mockito.*;

public class RegistrationCompleteEventListenerTest {

    @Mock
    private UserService userService;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private RegistrationCompleteEventListener listener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOnApplicationEvent() throws MessagingException {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setFirstName("Test");

        RegistrationCompleteEvent event = new RegistrationCompleteEvent(user, "http://localhost:8080");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        listener.onApplicationEvent(event);

        verify(userService, times(1)).saveUserVerificationToken(any(UserEntity.class), anyString());
        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }
}