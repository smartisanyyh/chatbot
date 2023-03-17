package com.chatbot.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * 参数配置表 sys_config
 *
 * @author ruoyi
 */
@Data
@Entity
public class SysConfig extends PanacheEntity {

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    private String configType;

}
