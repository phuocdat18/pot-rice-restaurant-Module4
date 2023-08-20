package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/404")
public class ErrorController {

    @GetMapping("/403")
    public String showAuthor() {
        return "shop/404";
    }
    @GetMapping()
    public String show404() {
        return "shop/404";
    }
}
