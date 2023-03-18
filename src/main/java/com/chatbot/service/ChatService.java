package com.chatbot.service;

import com.chatbot.domain.ApiKey;
import com.chatbot.domain.ChatBot;
import io.smallrye.mutiny.Uni;
import okhttp3.sse.EventSourceListener;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ChatService {
    @Inject
    ChatBot chatBot;
    @Inject
    ApiKey apiKey;

    public void completions(String prompt, EventSourceListener eventSourceListener) {
        chatBot.completions(apiKey.getRandomValidKey().getKey(), prompt, eventSourceListener);
    }

    public Uni<String> chat(String openId, String prompt, EventSourceListener eventSourceListener) {
        return chatBot.chat(apiKey.getRandomValidKey().getKey(), openId, prompt, eventSourceListener);
    }

}
