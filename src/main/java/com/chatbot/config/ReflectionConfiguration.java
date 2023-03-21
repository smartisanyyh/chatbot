package com.chatbot.config;

import com.chatbot.domain.dto.WeChatSessionDto;
import com.chatbot.rest.request.ChatRequest;
import com.unfbx.chatgpt.entity.chat.Message;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {WeChatSessionDto.class,
        Message.class,
        ChatRequest.class})
public class ReflectionConfiguration {
}
