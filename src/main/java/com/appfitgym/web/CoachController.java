package com.appfitgym.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoachController {

    @GetMapping("/coach/manager")
    public String coach() {
        return "create";
    }
}
