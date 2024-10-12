package com.example.controller;

import com.example.entity.SysUser;
import com.example.security.MyUserDetailsManager;
import com.example.service.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "用户管理")
@RequestMapping("/user")
public class UserController {

    @Autowired
    MyUserDetailsManager userDetailsManager;

    @Autowired
    UserService userService;

//    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "添加用户")
    @PostMapping("/add")
    public void add() {
        UserDetails build = User.builder().username("aaa").password("123").build();
        userDetailsManager.createUser(build);
    }

//    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "用户列表")
    @GetMapping("/list")
    public List<SysUser> list() {
        return userService.list();
    }

}
