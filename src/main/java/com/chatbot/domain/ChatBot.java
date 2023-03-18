package com.chatbot.domain;

import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.completions.Completion;
import okhttp3.sse.EventSourceListener;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.net.InetSocketAddress;
import java.net.Proxy;

@ApplicationScoped
public class ChatBot {

    public static final String HTTPS_API_OPENAI_COM = "https://api.openai.com/";

    @ConfigProperty(name = "proxy.enable", defaultValue = "false")
    Boolean enableProxy;

    @ConfigProperty(name = "proxy.host", defaultValue = "localhost")
    String proxyHost;
    @ConfigProperty(name = "proxy.port", defaultValue = "7654")
    Integer proxyPort;

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
    public void completions(String apiKey, String prompt, EventSourceListener eventSourceListener) {
        Completion q = Completion.builder()
                .prompt(prompt)
                .stream(true)
                .build();
        getClient(apiKey).streamCompletions(q, eventSourceListener);
    }


    private OpenAiStreamClient getClient(String apikey) {
        OpenAiStreamClient.Builder builder = OpenAiStreamClient.builder()
                .connectTimeout(50)
                .readTimeout(50)
                .writeTimeout(50)
                .apiKey(apikey)
                .apiHost(HTTPS_API_OPENAI_COM);
        if (enableProxy) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            builder.proxy(proxy);
        }
        return builder.build();
    }
}
