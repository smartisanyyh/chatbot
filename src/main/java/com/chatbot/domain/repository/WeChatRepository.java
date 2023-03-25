package com.chatbot.domain.repository;

import com.chatbot.domain.dto.MessageCheckRequestDto;
import com.chatbot.domain.dto.MessageCheckResponseDto;
import com.chatbot.domain.dto.WeChatAccessTokenDto;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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


    @GET
    @Path("/cgi-bin/token")
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<WeChatAccessTokenDto> accessToken(
            @QueryParam("grant_type") String grantType,
            @QueryParam("appid") String appid,
            @QueryParam("secret") String secret
    );

    @POST
    @Path("/wxa/msg_sec_check")
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<MessageCheckResponseDto> messageCheck(
            @QueryParam("access_token") String accessToken,
            MessageCheckRequestDto messageCheckRequestDto
    );

}
