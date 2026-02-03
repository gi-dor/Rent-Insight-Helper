package com.realestate.rent_insight.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 회원가입폼 입력 데이터
public class MemberDTO {
    private String email;
    private String password;
    private String name;
    private String nickname;
}
