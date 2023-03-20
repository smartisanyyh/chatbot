package com.chatbot.adapter;

import com.chatbot.rest.response.RestResponse;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import lombok.Data;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.time.LocalDateTime;

/**
 * 参数配置表 sys_config
 *
 * @author ruoyi
 */
@Data
@Entity
@Table(name = "answers")
@ApplicationScoped
public class AnswersEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    @Column(length = 2000)
    private String content;

    private LocalDateTime answerTime;


    @RolesAllowed("admin")
    @ResourceProperties(path = "answers")
    interface AnswersDefaultResource extends PanacheEntityResource<AnswersEntity, Long> {

        @Path("all")
        @PermitAll
        @GET
        default Object all() {
            return RestResponse.success(AnswersEntity.listAll());
        }
    }
}



