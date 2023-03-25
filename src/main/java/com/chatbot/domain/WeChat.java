package com.chatbot.domain;

import cn.hutool.json.JSONUtil;
import com.chatbot.common.constants.RedisKey;
import com.chatbot.common.enums.BizStatus;
import com.chatbot.domain.dto.MessageCheckRequestDto;
import com.chatbot.domain.dto.MessageCheckResponseDto;
import com.chatbot.domain.dto.WeChatAccessTokenDto;
import com.chatbot.domain.dto.WeChatSessionDto;
import com.chatbot.domain.repository.WeChatRepository;
import com.chatbot.exceptions.BizException;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.quarkus.runtime.util.StringUtil;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;

@ApplicationScoped
@Slf4j
public class WeChat {
    public static final String GRANT_TYPE = "authorization_code";
    public static final String CLIENT_CREDENTIAL = "client_credential";
    public static final String ACCESS_TOKEN_KEY = "access_token";

    @RestClient
    WeChatRepository weChatRepository;

    @ConfigProperty(name = "wx.appId")
    String appid;
    @ConfigProperty(name = "wx.secret")
    String secret;
    @Inject
    ReactiveRedisDataSource reactiveRedisDataSource;

    public Uni<WeChatSessionDto> login(String code) {
        ReactiveValueCommands<String, String> value = reactiveRedisDataSource.value(String.class);
        return weChatRepository.jsCodeToSession(appid, secret, code, GRANT_TYPE)
                .map(body -> JSONUtil.toBean(body, WeChatSessionDto.class))
                .map(Unchecked.function(res -> {
                    if (null != res.getErrcode()) {
                        throw new RuntimeException(res.getErrmsg());
                    }
                    return res;
                }))
                .call(session -> cacheSession(value, session));

    }

    private Uni<Void> cacheSession(ReactiveValueCommands<String, String> value, WeChatSessionDto session) {
        String key = RedisKey.LOGIN_USER_PREFIX + session.getOpenid();
        return value.set(key, JSONUtil.toJsonStr(session))
                .call(() -> reactiveRedisDataSource.key().expire(key, Duration.ofHours(12)));
    }


    public Uni<MessageCheckResponseDto> messageCheck(String message, String openId) {
        return accessToken().flatMap(a -> weChatRepository.messageCheck(a, MessageCheckRequestDto.builder()
                        .openid(openId)
                        .scene(1)
                        .version(2)
                        .content(message)
                        .build()))
                .invoke(Unchecked.consumer(a -> {
                    if (!"pass".equals(a.getResult().getSuggest())) {
                        throw new BizException(BizStatus.RISKY_CONTENT);
                    }
                }))
                ;

    }


    private Uni<String> accessToken() {
        ReactiveValueCommands<String, String> value = reactiveRedisDataSource.value(String.class);
        return value.get(ACCESS_TOKEN_KEY).call(cachedToken -> {
            if (!StringUtil.isNullOrEmpty(cachedToken)) {
                return Uni.createFrom().item(cachedToken);
            } else {
                //do get token
                return weChatRepository.accessToken(CLIENT_CREDENTIAL, appid, secret)
                        .onFailure().invoke(e -> log.error("occurred an error when fetch access token:", e))
                        .map(WeChatAccessTokenDto::getAccessToken)
                        //缓存到redis
                        .call(accessToken -> value.set(ACCESS_TOKEN_KEY, accessToken)
                                .call(() -> reactiveRedisDataSource.key().expire(ACCESS_TOKEN_KEY, Duration.ofSeconds(7100))));
            }
        });
    }

}
