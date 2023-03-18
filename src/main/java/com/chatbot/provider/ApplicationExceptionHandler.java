package com.chatbot.provider;

import com.chatbot.exceptions.BizException;
import com.chatbot.rest.response.RestResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionHandler implements ExceptionMapper<BizException> {

    @Override
    public Response toResponse(BizException exception) {
        RestResponse error = RestResponse.error(exception.getBizStatus());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(error)
                .build();
    }
}
