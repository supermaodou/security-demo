package com.example.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.entity.SysUser;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//    private static final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);
//
//    // 在这个类里面注入service，然后调用service的方法，获取用户信息
//    @Resource
//    private UserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SysUser::getUsername, username);
//        SysUser loginSysUser = userService.getOne(queryWrapper);
//        if (loginSysUser == null) {
//            log.info("登录用户：{} 不存在.", username);
//            throw new UsernameNotFoundException("用户不存在");
//        }
//        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN");
//        return new User(loginSysUser.getUsername(), loginSysUser.getPassword(), true, true, true, true, authorities);
//    }
//
//}
