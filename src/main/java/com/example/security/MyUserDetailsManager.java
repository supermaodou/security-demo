package com.example.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.entity.SysUser;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailsManager implements UserDetailsManager {

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsManager.class);

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails user) {
        SysUser sysUser = new SysUser(null, user.getUsername(), passwordEncoder.encode(user.getPassword()), 0);
        userService.save(sysUser);
    }

    @Override
    public void updateUser(UserDetails user) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysUser::getId, 3);
        updateWrapper.set(SysUser::getUsername, user.getUsername());
        updateWrapper.set(SysUser::getPassword, passwordEncoder.encode(user.getPassword()));
        userService.update(updateWrapper);
    }

    @Override
    public void deleteUser(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        userService.remove(queryWrapper);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // 先对比旧密码
        if (!passwordEncoder.matches(oldPassword, userService.getById(3).getPassword())) {
            throw new RuntimeException("旧密码不正确");
        }
        // 然后更新密码
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
            logger.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("用户不存在");
        }
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ADMIN", "USER");
        return new User(loginSysUser.getUsername(), loginSysUser.getPassword(), true, true, true, true, authorities);
    }
}
