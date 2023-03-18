package com.chatbot.domain;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChatBot {
    @PostConstruct
    void init() {
        System.out.println(111);
    }

    /**
     * 单次问答
     *
     * @return
     */
//    public String chat() {
//        return "";
//    }
//
//
//    public String completions() {
//
//    }


//    public void before() {
//        //代理可以不设置
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.111", 7890));
////        client = new OpenAiStreamClient("sk-**********************",
////                60,
////                60,
////                60,
////                proxy);
//        //推荐这种构造方式
//        OpenAiStreamClient build = OpenAiStreamClient.builder()
//                .connectTimeout(50)
//                .readTimeout(50)
//                .writeTimeout(50)
//                .apiKey("sk-******************************")
//                .proxy(proxy)
//                .apiHost("https://api.openai.com/")
//                .build();
//    }
//
//    public void chatCompletions() {
//        ConsoleEventSourceListener eventSourceListener = new ConsoleEventSourceListener();
//        Message message = Message.builder().role(Message.Role.USER).content("你好啊我的伙伴！").build();
//        ChatCompletion chatCompletion = ChatCompletion.builder().messages(Arrays.asList(message)).build();
//        client.streamChatCompletion(chatCompletion, eventSourceListener);
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void completions() {
//        ConsoleEventSourceListener eventSourceListener = new ConsoleEventSourceListener();
//        Completion q = Completion.builder()
//                .prompt("一句话描述下开心的心情")
//                .stream(true)
//                .build();
//        client.streamCompletions(q, eventSourceListener);
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
