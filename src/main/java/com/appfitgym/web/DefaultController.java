package com.appfitgym.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
