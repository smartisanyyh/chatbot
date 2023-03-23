package com.chatbot.domain.repository;

import com.chatbot.domain.dto.ApiKeyDto;

import java.util.List;

public interface ApiKeyRepository {

    List<ApiKeyDto> findAllApiKey();

    void persist(ApiKeyDto apiKeyDto);

    void delete(String apiKey);
}
