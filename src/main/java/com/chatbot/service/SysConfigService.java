package com.chatbot.service;

import com.chatbot.repository.SysConfig;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;

@RolesAllowed("admin")
public interface SysConfigService extends PanacheEntityResource<SysConfig, Long> {
    @PermitAll
    @Override
    default List<SysConfig> list(Page page, Sort sort) {
        return PanacheEntityResource.super.list(page, sort);
    }
}
