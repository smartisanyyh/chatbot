package com.chatbot.rest;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.json.JSONUtil;
import com.chatbot.common.constants.RedisKey;
import com.chatbot.rest.response.RestResponse;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import me.chanjar.weixin.common.error.WxErrorException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;


@Path("/wx")
public class WxResource {
    @Inject
    WxMaService wxMaService;

    @Inject
    RedisDataSource redisDataSource;

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object loginByCode(@PathParam("code") String code) throws WxErrorException {
        WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
        ValueCommands<String, String> value = redisDataSource.value(String.class);
        String key = RedisKey.LOGIN_USER_PREFIX + session.getOpenid();
        value.set(key, JSONUtil.toJsonStr(session));
        redisDataSource.key().expire(key, Duration.ofHours(12));
        return RestResponse.success(session.getOpenid());
    }
}