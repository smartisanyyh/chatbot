package com.chatbot.repository.converter;

import com.chatbot.domain.entity.ApiKeyEntity;
import com.chatbot.repository.ApiKeyRepositoryImpl;

public class ApiKeyConverter {

    public static ApiKeyEntity convert(ApiKeyRepositoryImpl apiKeyRepository) {
        ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
        apiKeyEntity.setStatus(apiKeyRepository.getStatus());
        apiKeyEntity.setKey(apiKeyRepository.getApikey());
        return apiKeyEntity;
    }
}
