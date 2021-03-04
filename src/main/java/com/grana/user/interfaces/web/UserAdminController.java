package com.grana.user.interfaces.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grana.user.domain.model.user.User;
import com.grana.user.infrastructure.persistence.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户
 *
 * @author Aaron Ma
 * @Date 2021-3-4 11:36:30
 */
@RestController
public class UserAdminController {
    @Autowired
    UserMapper userMapper;

    @PostMapping("/")
    public void insert(User user, HttpServletResponse response) {
        return;
    }

    @GetMapping("/user/{code}")
    public User selectUser(@PathVariable("code") String code) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_code", code));
    }
}
