package com.grana.user.infrastructure.persistence.mybatis.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grana.user.domain.model.user.User;
import com.grana.user.domain.model.user.UserRepository;
import com.grana.user.infrastructure.persistence.mybatis.mapper.UserAuthService;
import com.grana.user.infrastructure.persistence.mybatis.mapper.UserService;

/**
 * 用户聚合根持久层实现
 * 
 * @author Aaron Ma
 * @Date 2021-3-3 14:35:56
 */
@Repository
public class UserReposityImpl implements UserRepository {
    @Autowired
    UserService userService;
    @Autowired
    UserAuthService authSerive;

    @Transactional
    @Override
    public void insert(User user) {
        userService.save(user);
        authSerive.saveBatch(user.getUserAuths());
    }
    
    public User findUser() {
        return null;
    }
}
