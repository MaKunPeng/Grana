package com.dust.mailtest;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.grana.GranaApplication;
import com.grana.user.domain.model.user.IdentityType;
import com.grana.user.domain.model.user.User;
import com.grana.user.domain.model.user.UserAuth;
import com.grana.user.infrastructure.persistence.mybatis.mapper.UserAuthMapper;
import com.grana.user.infrastructure.persistence.mybatis.mapper.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GranaApplication.class)
public class RobotsApplicationTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserAuthMapper userAuthMapper;
    
    private static final Logger log = LoggerFactory.getLogger(RobotsApplicationTests.class);
    
    public void insertUser() {
        User user = new User();
        user.setUserCode("admin");
        user.setNickName("管理员");
        user.setSex(1);
        user.setStatus(0);
        userMapper.insert(user);
    }

    public void update() {
        UpdateWrapper<UserAuth> uw = new UpdateWrapper<>();
        uw.eq("id", 1366955345857830913L).set("login_ip", null);
        userAuthMapper.update(null, uw);
    }
    
    @Test
    public void testFill() {
        User user = userMapper.selectById(1366948261443518465L);
        user.setStatus(0);
        userMapper.updateById(user);
    }
    
    public void insertUserAuth() {
        UserAuth auth = new UserAuth();
        auth.setUserId(1366948261443518465L);
        auth.setIdentityType(IdentityType.PASSWORD);
        auth.setIdentifier("admin");
        auth.setCredentials("123456");
        userAuthMapper.insert(auth);
    }
}
