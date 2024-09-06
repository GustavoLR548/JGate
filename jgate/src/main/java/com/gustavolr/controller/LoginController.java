package com.gustavolr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    public final static String PAGE_PATH = "/login";

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
}
