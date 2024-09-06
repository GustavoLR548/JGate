package com.gustavolr.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    public final static String PAGE_PATH = "/error";

    @RequestMapping(PAGE_PATH)
    public String handleError() {
        return "error";
    }
}