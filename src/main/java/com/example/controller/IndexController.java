package com.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        return "Hello, " + authentication.getName() + "!";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }


}
