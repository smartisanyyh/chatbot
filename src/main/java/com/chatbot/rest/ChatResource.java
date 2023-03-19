package com.chatbot.rest;

import com.chatbot.common.constants.RedisKey;
import com.chatbot.rest.response.RestResponse;
import io.quarkus.redis.datasource.RedisDataSource;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
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
    @Path("resetChat/{openId}")
    public RestResponse resetChat(@PathParam("openId") String openId) {
        redisDataSource.key().del(RedisKey.LOGIN_USER_PREFIX + openId);
        return RestResponse.success();
    }
}
