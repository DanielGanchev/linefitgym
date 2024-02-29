package com.appfitgym.event.listener;

import com.appfitgym.event.RegistrationCompleteEvent;
import com.appfitgym.model.entities.UserEntity;

import com.appfitgym.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;



@Component

public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final UserService userService;

    private final JavaMailSender mailSender;

    private UserEntity theUser;
    private static final Logger log = LoggerFactory.getLogger(RegistrationCompleteEventListener.class);

    public RegistrationCompleteEventListener(UserService userService, JavaMailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
    }

    public UserService getUserService() {
        return userService;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public UserEntity getTheUser() {
        return theUser;
    }

    public RegistrationCompleteEventListener setTheUser(UserEntity theUser) {
        this.theUser = theUser;
        return this;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        theUser = event.getUserEntity();

        String verificationToken = UUID.randomUUID().toString();

        userService.saveUserVerificationToken(theUser, verificationToken);

        String url = event.getAppUrl()+"/users/register/verifyEmail?token="+verificationToken;

        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration :  {}", url);
    }
    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, "+ theUser.getFirstName()+ ", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("coltarq@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
