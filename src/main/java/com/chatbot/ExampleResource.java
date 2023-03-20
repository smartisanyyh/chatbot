package com.chatbot;

import cn.hutool.json.JSONUtil;
import com.chatbot.adapter.SysConfigEntity;
import com.chatbot.common.enums.ConfigType;
import com.unfbx.chatgpt.entity.chat.Message;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/hello")
public class ExampleResource {
    @Inject
    ReactiveRedisDataSource redisReactive;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("admin")
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("test")
    @Transactional
    public String test() {
        SysConfigEntity sysConfigRepository = new SysConfigEntity();
        sysConfigRepository.setConfigType(ConfigType.PUBLIC);
        SysConfigEntity.persist(sysConfigRepository);
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("test1")
    public Object test1() {
        ReactiveValueCommands<String, String> value = redisReactive.value(String.class);
        Uni<String> stringUni = value.get("1");
        String indefinitely = stringUni.await().indefinitely();
        List<Message> messages = JSONUtil.toList(indefinitely, Message.class);
        Message aa = Message.builder().role(Message.Role.SYSTEM).content("你是一个可以问答任何问题的全能机器人1" + System.currentTimeMillis()).build();
        messages.add(aa);
        value.set("1", JSONUtil.toJsonStr(messages)).await().indefinitely();

        return messages.size();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("test2")
    public Uni<String> test2() {
        ReactiveValueCommands<String, String> value = redisReactive.value(String.class);
        Uni<String> stringUni = value.get("1");
        Uni<String> invoke = stringUni.invoke(i -> {
            System.out.println(i);
            System.out.println(i);
            System.out.println(i);

        });
        return invoke;
//        String indefinitely = stringUni.await().indefinitely();
//        List<Message> messages = JSONUtil.toList(indefinitely, Message.class);
//        if (messages.size() >= 10) {
//            List<Message> messages1 = messages.subList(messages.size() - 10, messages.size());
//            value.set("1", JSONUtil.toJsonStr(messages1)).await().indefinitely();
//        }
//
//        return messages.size();
    }


}