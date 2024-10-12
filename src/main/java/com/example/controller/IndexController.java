package com.example.controller;

import com.example.security.MyUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

//    @GetMapping("index")
//    public Object index(Authentication authentication) {
//        return authentication;
//    }


    @GetMapping("/error")
    public String error() {
        return "error";
    }


}
