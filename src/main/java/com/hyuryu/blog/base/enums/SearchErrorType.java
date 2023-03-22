package com.hyuryu.blog.base.enums;

import lombok.Getter;

@Getter
public enum SearchErrorType {
    INTERNAL_ERROR("I999", "서버 내부 에러");

    String code;
    String message;

    SearchErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
