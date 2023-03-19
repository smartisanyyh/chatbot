package com.chatbot.event;

import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chatbot.rest.request.ChatRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

import javax.websocket.Session;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 描述： sse
 *
 * @author https:www.unfbx.com
 * 2023-02-28
 */
@Slf4j
public class WebSocketEventSourceListener extends EventSourceListener {

    Session session;
    ChatRequest chatRequest;
    StringBuilder response = new StringBuilder();
    Consumer<String> saveResponse;

    public WebSocketEventSourceListener(Session session, ChatRequest chatRequest) {
        this.session = session;
        this.chatRequest = chatRequest;
    }


    public void saveResponse(Consumer<String> saveResponse) {
        this.saveResponse = saveResponse;
    }

    @Override
    public void onOpen(EventSource eventSource, Response response) {
        log.info("OpenAI建立sse连接...");
    }

    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        try {
            if (data.equals("[DONE]")) {
                log.info("OpenAI response has finished");
                session.getAsyncRemote().sendObject(data);
                if (chatRequest.getIsChat()) {
                    saveResponse.accept(response.toString());
                    log.info("save history success");
                }
            } else {
                String jsonStr = EscapeUtil.unescape(data);
                JSONObject json = JSONUtil.parseObj(jsonStr);
                String word;
                if (chatRequest.getIsChat()) {
                    word = JSONUtil.getByPath(json, "choices[0].delta.content", "");
                } else {
                    word = JSONUtil.getByPath(json, "choices[0].text", "");
                }
                if (StrUtil.isNotBlank(word)) {
                    response.append(word);
                    session.getAsyncRemote().sendObject(word);
                }
            }
        } catch (Exception e) {
            log.error("parse error:{}", data);
        }
    }

    @Override
    public void onClosed(EventSource eventSource) {
        log.info("OpenAI关闭sse连接...");
    }

    @SneakyThrows
    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        if (Objects.isNull(response)) {
            log.error("OpenAI  sse连接异常:", t);
            session.getAsyncRemote().sendObject("网络异常请稍后重试.");
            session.getAsyncRemote().sendObject("[DONE]");
            eventSource.cancel();
            return;
        }
        ResponseBody body = response.body();
        if (Objects.nonNull(body)) {
            String bodyStr = body.string();
            log.error("OpenAI  sse连接异常data：{}，异常：{}", bodyStr, t);
            session.getAsyncRemote().sendObject(JSONUtil.getByPath(JSONUtil.parseObj(bodyStr), "error.code"));
            session.getAsyncRemote().sendObject("[DONE]");
        } else {
            log.error("OpenAI  sse连接异常data：{}，异常：{}", response, t);
        }
        eventSource.cancel();
    }
}