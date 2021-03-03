package com.grana.user.domain.model.user;

/**
 * 用户聚合根
 * 持久层统一接口标准
 * 
 * @author Aaron
 *
 */
public interface UserRepository {
    void insert(User user);
}
