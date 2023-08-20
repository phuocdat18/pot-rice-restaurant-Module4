package com.cg.controller;

import com.cg.exception.DataInputException;
import com.cg.model.Role;
import com.cg.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/login")
    public String showLogin() {
        return "login/sign_in";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "login/sign_up";
    }
}
