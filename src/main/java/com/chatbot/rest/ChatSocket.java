package com.chatbot.rest;

import com.chatbot.event.WebSocketEventSourceListener;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint("/chat/{openId}")
@ApplicationScoped
public class ChatSocket {
    @Inject
    ChatService chatService;
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("openId") String openId) {
//        WebSocketEventSourceListener webSocketEventSourceListener = ;
    }

    @OnClose
    public void onClose(Session session, @PathParam("openId") String openId) {
        sessions.remove(openId);
//        broadcast("User " + openId + " left");
    }

    @OnError
    public void onError(Session session, @PathParam("openId") String openId, Throwable throwable) {
        sessions.remove(openId);
//        broadcast("User " + openId + " left on error: " + throwable);
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("openId") String openId) {
        chatService.completions(message, new WebSocketEventSourceListener(session));
    }

//    private void broadcast(String message) {
//        sessions.values().forEach(s -> {
//            s.getAsyncRemote().sendObject();
//            s.getAsyncRemote().sendObject(message, result ->  {
//                if (result.getException() != null) {
//                    System.out.println("Unable to send message: " + result.getException());
//                }
//            });
//        });
//    }

}