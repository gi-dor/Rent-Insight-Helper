package com.realestate.rent_insight.common.exception;

import lombok.Getter;


// 회원가입시 발생하는 중복체크 때문에 만듬 - 나중에 로그 기록 남겨서 그래프로 조회해야징
@Getter
public class DuplicateFieldException extends RuntimeException{

    private final String field; // 중복이 발생한 필드명 (예: "email", "nickname")

    public DuplicateFieldException(String field, String message) {
        super(message);
        this.field = field;
    }
}
