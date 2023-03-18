package com.chatbot.common.enums;

public enum BizStatus {
    SUCCESS(0, "成功"),
    NO_NORMAL_KEY_NOW(10001, "暂无可用key"),
    ;
    private final Integer code;
    private final String message;

    BizStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
