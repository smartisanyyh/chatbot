package com.chatbot.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class MessageCheckResponseDto {
    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;
    @JsonProperty("result")
    private ResultDTO result;
    @JsonProperty("detail")
    private List<DetailDTO> detail;
    @JsonProperty("trace_id")
    private String traceId;

    @NoArgsConstructor
    @Data
    public static class ResultDTO {
        @JsonProperty("suggest")
        private String suggest;
        @JsonProperty("label")
        private Integer label;
    }

    @NoArgsConstructor
    @Data
    public static class DetailDTO {
        @JsonProperty("strategy")
        private String strategy;
        @JsonProperty("errcode")
        private Integer errcode;
        @JsonProperty("suggest")
        private String suggest;
        @JsonProperty("label")
        private Integer label;
        @JsonProperty("prob")
        private Integer prob;
        @JsonProperty("level")
        private Integer level;
        @JsonProperty("keyword")
        private String keyword;
    }
}
