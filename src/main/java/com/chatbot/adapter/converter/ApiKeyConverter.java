package com.chatbot.adapter.converter;

import com.chatbot.adapter.ApiKeyEntity;
import com.chatbot.domain.dto.ApiKeyDto;

public class ApiKeyConverter {

    public static ApiKeyDto convert(ApiKeyEntity apiKeyRepository) {
        ApiKeyDto apiKeyDto = new ApiKeyDto();
        apiKeyDto.setStatus(apiKeyRepository.getStatus());
        apiKeyDto.setKey(apiKeyRepository.getApikey());
        return apiKeyDto;
    }
}
