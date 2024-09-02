package com.gustavolr.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Retorna uma página HTML personalizada ou uma mensagem
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}