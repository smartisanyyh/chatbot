package com.chatbot.domain;

import cn.hutool.json.JSONUtil;
import com.chatbot.common.constants.RedisKey;
import com.chatbot.domain.dto.WeChatSessionDto;
import com.chatbot.domain.repository.WeChatRepository;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
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


}
