package com.chatbot.rest;


import com.chatbot.domain.ApiKey;
import com.chatbot.rest.response.RestResponse;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("key")
@RolesAllowed("admin")
public class ApiKeyResource {
    @Inject
    ApiKey apiKey;

    @GET
    public Object keys() {
        return RestResponse.success(apiKey.keys());
    }


    @DELETE
    @Path("{key}")
    @Transactional
    public Object remove(@PathParam("key") String key) {
        apiKey.delete(key);
        return RestResponse.success();
    }

    @POST
    @Path("{key}")
    @Transactional
    public Object add(@PathParam("key") String key) {
        apiKey.add(key);
        return RestResponse.success();
    }

}
