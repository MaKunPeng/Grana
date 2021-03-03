package com.grana.user.infrastructure.persistence.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源管理
 * 
 * @author Aaron Ma
 * @Date 2021-3-2 11:2:24
 */
public class DynamicDataSourceContextHolder {
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);
    
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();
    
    /**
     * 设置当前数据源
     * @param type
     */
    public static void setDataSourceType(String type) {
        log.info("切换到{}数据源", type);
        CONTEXT_HOLDER.set(type);
    }
    
    /**
     * 获取当前数据源类型
     * @return
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }
    
    /**
     * 清空数据源变量
     */
    public static void cleanDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}
