package com.chatbot.repository;

import com.chatbot.domain.SysConfig;
import com.chatbot.domain.repository.ConfigRepository;
import com.chatbot.repository.converter.ConfigConverter;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.rest.data.panache.ResourceProperties;
import lombok.Data;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

/**
 * 参数配置表 sys_config
 *
 * @author ruoyi
 */
@Data
@Entity
@Table(name = "sys_config")
public class SysConfigRepositoryImpl extends PanacheEntity implements ConfigRepository {

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

    @Override
    public Optional<SysConfig> getConfigByKey(String configKey) {
        Optional<SysConfigRepositoryImpl> config = SysConfigRepositoryImpl.find("config_key", configKey).firstResultOptional();
        return config.map(ConfigConverter::convert);
    }


    @RolesAllowed("admin")
    @ResourceProperties(path = "sys/config")
    interface SysConfigDefaultResource extends PanacheEntityResource<SysConfigRepositoryImpl, Long> {
        @PermitAll
        @Override
        default List<SysConfigRepositoryImpl> list(Page page, Sort sort) {
            return PanacheEntityResource.super.list(page, sort);
        }
    }
}



