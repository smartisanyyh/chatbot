package com.chatbot.rest;

import com.chatbot.domain.ApiKey;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/chat")
public class ChatResource {
    @Inject
    ApiKey chatBot;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Object hello() {
        return chatBot.getRandomValidKey();
    }
}