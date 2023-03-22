package com.hyuryu.blog.base.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class CodeResponse {
    public final static String SUCCESS = "0";
    public final static String FAIL = "9999";

    @JsonProperty("code")
    String code;

    @JsonProperty("message")
    String message;

    @JsonProperty("result")
    Object result;

    public static CodeResponse successResult(Object result) {
        return CodeResponse.builder()
                .code(SUCCESS)
                .result(result)
                .build();
    }
}
