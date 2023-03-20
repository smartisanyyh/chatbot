package com.chatbot.domain.dto;

import com.chatbot.domain.enums.KeyStatus;
import lombok.Data;

@Data
public class ApiKeyDto {
    private KeyStatus status;
    private String key;
}
