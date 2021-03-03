package com.grana.user.infrastructure.persistence.mybatis;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 自动填充创建时间、修改时间
 * 
 * @author Aaron Ma
 * @Date 2021-3-3 11:53:51
 */
@Component
public class AutoFillMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 如果原来有值，则不会更新
//        this.setFieldValByName("updateTime", new Date(), metaObject);
        // 即使原来有值，也更新为当前时间  主要官网给的是LocalDataTime.class 这个要求你的updateTime字段类型也是localDateTime，不然就是null了
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}
