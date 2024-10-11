package com.example.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.entity.SysUser;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsManager implements UserDetailsManager {

    private static final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails user) {
        SysUser sysUser = new SysUser(null, user.getUsername(), passwordEncoder.encode(user.getPassword()));
        userService.save(sysUser);
    }

    @Override
    public void updateUser(UserDetails user) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, user.getUsername());
        queryWrapper.eq(SysUser::getPassword, passwordEncoder.encode(user.getPassword()));
        userService.update(queryWrapper);
    }

    @Override
    public void deleteUser(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        userService.remove(queryWrapper);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysUser::getPassword, newPassword);
        userService.update(updateWrapper);
    }

    @Override
    public boolean userExists(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = userService.getOne(queryWrapper);
        return sysUser == null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser loginSysUser = userService.getOne(queryWrapper);
        if (loginSysUser == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("用户不存在");
        }
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN");
        return new User(loginSysUser.getUsername(), loginSysUser.getPassword(), true, true, true, true, authorities);
    }
}
