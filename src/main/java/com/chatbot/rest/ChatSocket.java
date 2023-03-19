package com.chatbot.rest;

import com.chatbot.event.WebSocketEventSourceListener;
import com.chatbot.rest.decoder.ChatRequestDecoder;
import com.chatbot.rest.request.ChatRequest;
import com.chatbot.service.ChatService;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.smallrye.mutiny.Uni;
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
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@ServerEndpoint(value = "/chat/{openId}", decoders = {ChatRequestDecoder.class})
@ApplicationScoped
public class ChatSocket {
    @Inject
    ChatService chatService;
    @Inject
    ReactiveRedisDataSource redisDataSource;

    @OnOpen
    public void onOpen(Session session, @PathParam("openId") String openId) {
        checkSession(session, openId).subscribeAsCompletionStage();
    }

    private Uni<Boolean> checkSession(Session session, String openId) {
        String key = "loginUser:" + openId;
        return redisDataSource.key().exists(key).call(e -> {
            if (!e) {
                return Uni.createFrom().future(session.getAsyncRemote().sendObject("[NO__AUTH]"))
                        .call(() -> Uni.createFrom().completionStage(CompletableFuture.runAsync(() -> {
                            try {
                                session.close();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        })));
            } else {
                return Uni.createFrom().nothing();
            }
        });
    }

    @OnClose
    public void onClose(Session session, @PathParam("openId") String openId) {
    }

    @OnError
    public void onError(Session session, @PathParam("openId") String openId, Throwable throwable) {
    }

    @OnMessage
    public void onMessage(Session session, ChatRequest chatRequest) {
        checkSession(session, chatRequest.getOpenId()).call(() -> {
            if (chatRequest.getIsChat()) {
                return chatService.chat(chatRequest.getOpenId(), chatRequest.getPrompt(), new WebSocketEventSourceListener(session, chatRequest));
            } else {
                return Uni.createFrom()
                        .completionStage(CompletableFuture.runAsync(() -> chatService.completions(chatRequest.getPrompt(), new WebSocketEventSourceListener(session, chatRequest))));
            }
        });

    }

}