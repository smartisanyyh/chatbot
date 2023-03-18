package com.chatbot.rest.request;

import lombok.Data;

@Data
public class ChatRequest {
    String openId;
    String prompt;
    Boolean isChat;
}
