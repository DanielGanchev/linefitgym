package com.appfitgym.web;


import static com.appfitgym.service.impl.UserServiceImpl.getLoggedUserId;

import com.appfitgym.service.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserService userService;



    public GlobalControllerAdvice(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userId")
    public Long getUserId() {
        return getLoggedUserId();
    }
}
