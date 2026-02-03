package com.realestate.rent_insight.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 회원가입폼 입력 데이터
public class MemberDTO {
    // 1. 이름: 한글만 허용 (자음/모음 낱자 제외, 완성형 한글만)
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글로만 입력해주세요.")
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하이어야 합니다.")
    private String name;

    // 2. 닉네임: 특수문자 제외, 2~10자
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]+$", message = "닉네임은 특수문자를 사용할 수 없습니다.")
    private String nickname;

    // 3. 이메일: 이메일 형식 검사
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    // 4. 비밀번호: 영문, 숫자, 특수문자 포함 8자 이상
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.")
    private String password;

}
