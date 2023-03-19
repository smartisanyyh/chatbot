package com.chatbot.repository;

import com.chatbot.domain.entity.ApiKeyEntity;
import com.chatbot.domain.enums.KeyStatus;
import com.chatbot.domain.repository.ApiKeyRepository;
import com.chatbot.repository.converter.ApiKeyConverter;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import lombok.Data;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 参数配置表 sys_config
 *
 * @author ruoyi
 */
@Data
@Entity
@Table(name = "api_key")
@ApplicationScoped
public class ApiKeyRepositoryImpl extends PanacheEntityBase implements ApiKeyRepository {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * 参数名称
     */
    private String apikey;
    /**
     * 参数键名
     */
    private KeyStatus status;

    @Override
    public List<ApiKeyEntity> findAllApiKey() {
        return ApiKeyRepositoryImpl.<ApiKeyRepositoryImpl>listAll().stream().map(ApiKeyConverter::convert).collect(Collectors.toList());
    }


    @RolesAllowed("admin")
    @ResourceProperties(path = "sys/apikey")
    interface ApiKeyDefaultResource extends PanacheEntityResource<ApiKeyRepositoryImpl, Long> {

    }
}



