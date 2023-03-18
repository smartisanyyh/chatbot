package com.chatbot.rest;

import com.chatbot.event.WebSocketEventSourceListener;
import com.chatbot.rest.decoder.ChatRequestDecoder;
import com.chatbot.rest.request.ChatRequest;
import com.chatbot.service.ChatService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Slf4j
@ServerEndpoint(value = "/chat/{openId}", decoders = {ChatRequestDecoder.class})
@ApplicationScoped
public class ChatSocket {
    @Inject
    ChatService chatService;

    @OnOpen
    public void onOpen(Session session, @PathParam("openId") String openId) {
    }

    @OnClose
    public void onClose(Session session, @PathParam("openId") String openId) {
    }

    @OnError
    public void onError(Session session, @PathParam("openId") String openId, Throwable throwable) {
    }

    @OnMessage
    public void onMessage(Session session, ChatRequest chatRequest) {
        if (chatRequest.getIsChat()) {
            chatService.chat(chatRequest.getOpenId(), chatRequest.getPrompt(), new WebSocketEventSourceListener(session, chatRequest))
                    .subscribe().asCompletionStage();
        } else {
            chatService.completions(chatRequest.getPrompt(), new WebSocketEventSourceListener(session, chatRequest));
        }
    }

}