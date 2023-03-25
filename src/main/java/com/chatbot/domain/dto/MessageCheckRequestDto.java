package com.chatbot.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class MessageCheckRequestDto {
    @JsonProperty("openid")
    private String openid;
    @JsonProperty("scene")
    private Integer scene;
    @JsonProperty("version")
    private Integer version;
    @JsonProperty("content")
    private String content;
}
