package com.chatbot.domain.dto;

import com.chatbot.domain.enums.KeyStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiKeyDto {
    private KeyStatus status;
    private String key;
}
