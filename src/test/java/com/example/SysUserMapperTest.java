package com.example;

import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysUserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    void test() {
        System.out.println(userMapper.selectList(null));
    }

    @Test
    void test2() {
        System.out.println(userService.list());
    }
}
