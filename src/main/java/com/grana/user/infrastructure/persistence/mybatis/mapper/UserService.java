package com.grana.user.infrastructure.persistence.mybatis.mapper;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grana.user.domain.model.user.User;

/**
 * User Pesistence Service
 * @author Aaron Ma
 * @Date 2021-3-3 15:9:32
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}
