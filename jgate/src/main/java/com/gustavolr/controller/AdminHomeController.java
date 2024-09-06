package com.gustavolr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {

    public static final String HOME_PATH = "/admin/home";

    @GetMapping(HOME_PATH)
    public String home() {
        return "admin_home";
    }
}