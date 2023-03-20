package com.chatbot.rest;

import com.chatbot.domain.WeChat;
import com.chatbot.domain.dto.WeChatSessionDto;
import com.chatbot.rest.response.RestResponse;
import io.smallrye.mutiny.Uni;
import me.chanjar.weixin.common.error.WxErrorException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/wx")
public class WxResource {
    @Inject
    WeChat weChat;

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<RestResponse> loginByCode(@PathParam("code") String code) throws WxErrorException {
        return RestResponse.success(weChat.login(code).map(WeChatSessionDto::getOpenid));
    }
}