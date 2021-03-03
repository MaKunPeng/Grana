package com.grana.user.infrastructure.persistence.datasource;

/**
 * 数据源类型枚举
 * 
 * @author Aaron Ma
 *
 */
public enum DataSourceType {
    /**
     * 主库
     */
    MASTER,
    
    /**
     * 从库
     */
    SLAVE
}
