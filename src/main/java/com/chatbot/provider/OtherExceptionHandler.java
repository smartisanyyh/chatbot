package com.chatbot.provider;

import com.chatbot.common.enums.BizStatus;
import com.chatbot.rest.response.RestResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Slf4j
public class OtherExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        log.error("error", exception);
        RestResponse error = new RestResponse(null, BizStatus.INTERNAL_SERVER_ERROR.getCode(), exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(error)
                .build();
    }
}
