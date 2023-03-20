package com.chatbot.event;


import com.chatbot.domain.ApiKey;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class Startup {
    @Inject
    ApiKey apiKey;

    public void loadApikey(@Observes StartupEvent evt) {
        apiKey.init();
    }
}