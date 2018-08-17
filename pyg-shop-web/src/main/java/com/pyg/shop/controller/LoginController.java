package com.pyg.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/getName")
    public User showLoginName(){
        User User = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return User;
    }
}
