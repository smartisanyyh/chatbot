package com.chatbot.rest;

import cn.hutool.json.JSONUtil;
import com.chatbot.common.constants.RedisKey;
import com.chatbot.rest.response.RestResponse;
import com.unfbx.chatgpt.entity.chat.Message;
import io.quarkus.redis.datasource.RedisDataSource;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Path("chat")
public class ChatResource {
    @Inject
    RedisDataSource redisDataSource;

    @DELETE
    @Path("history/{openId}")
    public RestResponse resetChat(@PathParam("openId") String openId) {
        redisDataSource.key().del(RedisKey.CHAT_HISTORY_PREFIX + openId);
        return RestResponse.success();
    }

    @GET
    @Path("history/{openId}")
    public RestResponse chatHistory(@PathParam("openId") String openId) {
        return RestResponse.success(JSONUtil.toList(redisDataSource.value(String.class)
                .get(RedisKey.CHAT_HISTORY_PREFIX + openId), Message.class));
    }
}
