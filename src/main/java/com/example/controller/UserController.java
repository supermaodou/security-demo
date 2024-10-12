package com.example.controller;

import com.example.security.MyUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MyUserDetailsManager userDetailsManager;

    @PostMapping("/add")
    public void add() {
        UserDetails build = User.builder().username("aaa").password("123").build();
        userDetailsManager.createUser(build);
    }

}
