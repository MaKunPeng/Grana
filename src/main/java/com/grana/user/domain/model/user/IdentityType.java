package com.grana.user.domain.model.user;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 凭证类型
 * @author Aaron Ma
 * @Date 2021-3-3 11:20:39
 */
public enum IdentityType {
    PASSWORD("password"),
    EMAIL("email"),
    PHONE("phone"),
    QQ("qq"),
    WEIIXIN("weixin");
    
    @EnumValue
    private final String code;
    
    public String getCode() {
        return this.code;
    }
    
    IdentityType(String code) {
        this.code = code;
    }
}
