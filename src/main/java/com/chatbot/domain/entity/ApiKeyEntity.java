package com.chatbot.domain.entity;

import com.chatbot.domain.enums.KeyStatus;
import lombok.Data;

@Data
public class ApiKeyEntity {
    private KeyStatus status;
    private String key;
}
