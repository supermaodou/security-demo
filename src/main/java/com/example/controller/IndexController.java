package com.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

//    @GetMapping({"/", "/index"})
//    public String index() {
//        return "index";
//    }

    @GetMapping("index")
    public Object index(Authentication authentication) {
        return authentication;
    }

//    @RequestMapping("/login")
//    public String login() {
//        return "login";
//    }

    @GetMapping("/404")
    public String e404() {
        return "404";
    }

}
