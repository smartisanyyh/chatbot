package com.chatbot.domain;

import com.chatbot.common.enums.BizStatus;
import com.chatbot.domain.entity.ApiKeyEntity;
import com.chatbot.domain.enums.KeyStatus;
import com.chatbot.domain.repository.ApiKeyRepository;
import com.chatbot.exceptions.BizException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class ApiKey {
    @Inject
    ApiKeyRepository apiKeyRepository;
    private List<ApiKeyEntity> keys;

    @PostConstruct
    public void init() {
        keys = apiKeyRepository.findAllApiKey();
    }

    public ApiKeyEntity getRandomValidKey() {
        List<ApiKeyEntity> collect = keys.stream().filter(i -> i.getStatus() == KeyStatus.NORMAL).toList();
        if (collect.size() == 0) {
            throw new BizException(BizStatus.NO_NORMAL_KEY_NOW);
        }
        return collect.get(new Random().nextInt(collect.size()));
    }

}
