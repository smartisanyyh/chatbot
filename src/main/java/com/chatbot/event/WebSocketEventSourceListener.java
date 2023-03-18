package com.chatbot.event;

import cn.hutool.core.util.EscapeUtil;
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
    StringBuilder response;
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
            if (data.equals("[DONE]") && chatRequest.getIsChat()) {
                log.info("OpenAI response has finished");
                saveResponse.accept(response.toString());
                log.info("save his success");
            } else {
                String jsonStr = EscapeUtil.unescape(data);
                JSONObject json = JSONUtil.parseObj(jsonStr);
                String word = JSONUtil.getByPath(json, "choices[0].text").toString();
                response.append(word);
                session.getAsyncRemote().sendObject(word);
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
            log.error("OpenAI  sse连接异常:{}", t);
            eventSource.cancel();
            return;
        }
        ResponseBody body = response.body();
        if (Objects.nonNull(body)) {
            log.error("OpenAI  sse连接异常data：{}，异常：{}", body.string(), t);
        } else {
            log.error("OpenAI  sse连接异常data：{}，异常：{}", response, t);
        }
        eventSource.cancel();
    }
}