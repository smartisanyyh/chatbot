package com.chatbot.domain.repository;

import com.chatbot.domain.entity.ApiKeyEntity;

import java.util.List;

public interface ApiKeyRepository {

    List<ApiKeyEntity> findAllApiKey();
}
