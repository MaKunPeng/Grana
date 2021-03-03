package com.grana.user.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grana.user.domain.model.user.UserAuth;
import org.springframework.stereotype.Service;

/**
 * UserAuth Pesistence Service
 * @author Aaron Ma
 * @Date 2021-3-3 15:9:32
 */
@Service
public class UserAuthService extends ServiceImpl<UserAuthMapper, UserAuth> {

}
