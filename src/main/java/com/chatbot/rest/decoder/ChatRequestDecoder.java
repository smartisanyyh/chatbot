package com.chatbot.rest.decoder;


import cn.hutool.json.JSONUtil;
import com.chatbot.rest.request.ChatRequest;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;


public class ChatRequestDecoder implements Decoder.Text<ChatRequest> {

    @Override
    public ChatRequest decode(String s) {
        return JSONUtil.toBean(s, ChatRequest.class);
    }

    @Override
    public boolean willDecode(String s) {

        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // do nothing.
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
