package com.gustavolr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {

    @GetMapping("/admin/home")
    public String home() {
        return "admin_home";
    }
}