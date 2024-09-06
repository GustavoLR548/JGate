package com.gustavolr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserHomeController {

    public final static String HOME_PATH = "/user/home";    

    @GetMapping(HOME_PATH)
    public String home() {
        return "user_home";
    }
}