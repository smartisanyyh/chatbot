package com.chatbot.rest.response;

import com.chatbot.common.enums.BizStatus;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.mutiny.Uni;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@RegisterForReflection
@Getter
@Slf4j
public class RestResponse implements Serializable {
    private final Integer code;
    private final String message;
    private Object data;

    public RestResponse(Object data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public RestResponse(BizStatus bizStatus, Object data) {
        this.data = data;
        this.code = bizStatus.getCode();
        this.message = bizStatus.getMessage();
    }

    public RestResponse(BizStatus bizStatus) {
        this.code = bizStatus.getCode();
        this.message = bizStatus.getMessage();
    }

    public static RestResponse success(Object data) {
        return new RestResponse(BizStatus.SUCCESS, data);
    }


    public static <T> Uni<RestResponse> success(Uni<T> uni) {
        return uni.onItem().transform(RestResponse::success);

    }


    public static RestResponse success() {
        return new RestResponse(BizStatus.SUCCESS, null);
    }

    public static RestResponse error(BizStatus bizStatus) {
        return new RestResponse(bizStatus);
    }


    public static RestResponse error(Throwable exception) {
        return new RestResponse(null, BizStatus.INTERNAL_SERVER_ERROR.getCode(), exception.getMessage());
    }


}
