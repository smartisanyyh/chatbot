package com.chatbot.config;

import com.chatbot.domain.dto.MessageCheckResponseDto;
import com.chatbot.domain.dto.WeChatAccessTokenDto;
import com.chatbot.domain.dto.WeChatSessionDto;
import com.chatbot.rest.request.ChatRequest;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.entity.completions.Completion;
import com.unfbx.chatgpt.entity.completions.CompletionResponse;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {WeChatSessionDto.class,
        Message.class,
        ChatRequest.class,
        ChatCompletion.class,
        ChatCompletionResponse.class,
        Completion.class,
        MessageCheckResponseDto.class,
        MessageCheckResponseDto.class,
        WeChatAccessTokenDto.class,
        CompletionResponse.class})
public class ReflectionConfiguration {
}
