package com.chatbot.repository;

import com.chatbot.common.enums.ConfigType;
import com.chatbot.domain.SysConfig;
import com.chatbot.domain.repository.ConfigRepository;
import com.chatbot.repository.converter.ConfigConverter;
import com.chatbot.rest.response.RestResponse;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import lombok.Data;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Optional;

/**
 * 参数配置表 sys_config
 *
 * @author ruoyi
 */
@Data
@Entity
@Table(name = "sys_config")
public class SysConfigRepositoryImpl extends PanacheEntityBase implements ConfigRepository {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

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
    private ConfigType configType;

    @Override
    public Optional<SysConfig> getConfigByKey(String configKey) {
        Optional<SysConfigRepositoryImpl> config = SysConfigRepositoryImpl.find("config_key", configKey).firstResultOptional();
        return config.map(ConfigConverter::convert);
    }


    @RolesAllowed("admin")
    @ResourceProperties(path = "sys/config")
    interface SysConfigDefaultResource extends PanacheEntityResource<SysConfigRepositoryImpl, Long> {
        @PermitAll
        @Path("public")
        @GET
        default Object getPublicConfig() {
            return RestResponse.success(SysConfigRepositoryImpl.list("config_type", ConfigType.PUBLIC.ordinal()));
        }


    }
}



