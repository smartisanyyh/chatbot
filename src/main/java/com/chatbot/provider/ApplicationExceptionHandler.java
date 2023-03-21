package com.chatbot.provider;

import com.chatbot.exceptions.BizException;
import com.chatbot.rest.response.RestResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Slf4j
public class ApplicationExceptionHandler implements ExceptionMapper<BizException> {

    @Override
    public Response toResponse(BizException exception) {
        log.error("biz error", exception);
        RestResponse error = RestResponse.error(exception.getBizStatus());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(error)
                .build();
    }
}
