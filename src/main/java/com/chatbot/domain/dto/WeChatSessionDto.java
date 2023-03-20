package com.chatbot.domain.dto;

import lombok.Data;

@Data
public class WeChatSessionDto {

    private String sessionKey;

    private String openid;

    private String unionid;

    private String errmsg;
    private String errcode;

}
