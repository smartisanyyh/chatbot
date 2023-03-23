package com.chatbot.adapter.converter;

import com.chatbot.adapter.ApiKeyEntity;
import com.chatbot.domain.dto.ApiKeyDto;

public class ApiKeyConverter {

    public static ApiKeyDto convert(ApiKeyEntity apiKeyEntity) {
        return ApiKeyDto.builder()
                .key(apiKeyEntity.getApikey())
                .status(apiKeyEntity.getStatus())
                .build();
    }


    public static ApiKeyEntity convert(ApiKeyDto apiKeyDto) {
        ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
        apiKeyEntity.setApikey(apiKeyDto.getKey());
        apiKeyEntity.setStatus(apiKeyDto.getStatus());
        return apiKeyEntity;
    }
}
