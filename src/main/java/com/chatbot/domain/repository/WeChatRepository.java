package com.chatbot.domain.repository;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@RegisterRestClient(configKey = "wechat")
public interface WeChatRepository {
    @GET
    @Path("/sns/jscode2session")
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<String> jsCodeToSession(
            @QueryParam("appid") String appid,
            @QueryParam("secret") String secret,
            @QueryParam("js_code") String jsCode,
            @QueryParam("grant_type") String authorizationCode
    );
}
