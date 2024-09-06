package com.gustavolr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    public final static String PAGE_PATH = "/";

    @GetMapping(PAGE_PATH)
    public String home() {
        return "index";
    }
}