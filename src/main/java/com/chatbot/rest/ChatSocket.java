package com.chatbot.rest;

import com.chatbot.common.constants.RedisKey;
import com.chatbot.domain.WeChat;
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
    @Inject
    WeChat weChat;

    @OnOpen
    public void onOpen(Session session, @PathParam("openId") String openId) {
        checkSession(session, openId).subscribeAsCompletionStage();
    }

    private Uni<Void> checkSession(Session session, String openId) {
        String key = RedisKey.LOGIN_USER_PREFIX + openId;
        return redisDataSource.key().exists(key).chain(e -> {
            if (e) {
                //校验通过 继续执行
                return Uni.createFrom().voidItem();
            } else {
                //校验失败关闭socket
                return Uni.createFrom()
                        .future(() -> session.getAsyncRemote().sendObject("[NO__AUTH]"))
                        .chain(() -> Uni.createFrom().future(() -> CompletableFuture.runAsync(() -> {
                            try {
                                session.close();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        })));
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
        checkSession(session, chatRequest.getOpenId())
                .chain(() -> weChat.messageCheck(chatRequest.getPrompt(), chatRequest.getOpenId()))
                .chain(() -> processChatRequest(session, chatRequest))
                .onFailure().invoke(e -> {
                    session.getAsyncRemote().sendObject("ERROR:" + e.getMessage());
                    session.getAsyncRemote().sendObject("[DONE]");
                })
                .subscribe().asCompletionStage();

    }

    private Uni<?> processChatRequest(Session session, ChatRequest chatRequest) {
        if (chatRequest.getIsChat()) {
            return chatService.chat(chatRequest.getOpenId(), chatRequest.getPrompt(), new WebSocketEventSourceListener(session, chatRequest));
        } else {
            return Uni.createFrom()
                    .completionStage(() -> CompletableFuture.runAsync(() -> chatService.completions(chatRequest.getPrompt(),
                            new WebSocketEventSourceListener(session, chatRequest))));
        }
    }

}