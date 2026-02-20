package com.realestate.rent_insight.common.exception;

import lombok.Getter;


// 회원가입시 발생하는 id(이메일)와 닉네임 설정시 중복체크 때문에 만듬 - 나중에 로그 기록 남겨서 그래프로 조회해야징
@Getter
public class DuplicateFieldException extends RuntimeException{

    private final String field; // 중복이 발생한 필드명 (예: "email", "nickname")

    public DuplicateFieldException(String field, String message) {
        super(message); // 예외가 발생하면 생성자로 인해 예외 메시지가 전달된다
        this.field = field;
    }
    /**
     * Duplicate ~~~
     * field : "email"
     * message : "이미 사용중인 이메일 "
     */
}
