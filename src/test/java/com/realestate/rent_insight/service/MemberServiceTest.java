package com.realestate.rent_insight.service;

import com.realestate.rent_insight.domain.entity.Member;
import com.realestate.rent_insight.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @ExtendWith(MockitoExtension.class): JUnit 5에서 Mockito를 사용하겠다고 선언하는 어노테이션입니다.
 *                                     이걸 붙여야 @Mock, @InjectMocks 같은 어노테이션이 동작합니다.
 */
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    /**
     * @InjectMocks: '가짜 객체(@Mock)를 주입할 대상'을 지정합니다.
     *               여기서는 MemberService가 테스트의 주인공입니다.
     *               Mockito는 @Mock으로 만들어진 MemberRepository와 PasswordEncoder를
     *               이 MemberService에 자동으로 주입해 줍니다.
     */
    @InjectMocks
    private MemberService memberService;

    /**
     * @Mock: 가짜(Mock) 객체를 만듭니다. 실제 객체처럼 동작하지만, 우리가 원하는 대로 행동을 정의할 수 있습니다.
     *        실제 DB에 연결하지 않고 테스트하기 위해 사용합니다.
     */
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 성공 테스트")
    void login_success() {
        // given (준비): 테스트에 필요한 환경을 설정합니다.
        String email = "test@test.com";
        String rawPassword = "password123"; // 사용자가 입력한 날것의 비밀번호
        String encodedPassword = "encodedPassword"; // DB에 저장된 암호화된 비밀번호

        // 1. 테스트용 Member 객체를 만듭니다.
        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        // 2. Mock 객체의 행동을 정의합니다.
        // "memberRepository.findByEmail(email)이 호출되면, 위에서 만든 member 객체를 반환해라" 라고 설정
        given(memberRepository.findByEmail(email)).willReturn(member);
        // "passwordEncoder.matches(rawPassword, encodedPassword)가 호출되면, true를 반환해라" 라고 설정
        given(passwordEncoder.matches(rawPassword, encodedPassword)).willReturn(true);

        // when (실행): 실제 테스트할 메서드를 호출합니다.
        Member result = memberService.login(email, rawPassword);

        // then (검증): 실행 결과를 확인합니다.
        assertThat(result).isNotNull(); // 결과가 null이 아니어야 함 (로그인 성공)
        assertThat(result.getEmail()).isEqualTo(email); // 결과 객체의 이메일이 예상과 같아야 함
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 비밀번호 틀림")
    void login_fail_wrong_password() {
        // given (준비)
        String email = "test@test.com";
        String rawPassword = "wrongPassword"; // 사용자가 입력한 틀린 비밀번호
        String encodedPassword = "encodedPassword";

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        // "memberRepository.findByEmail(email)이 호출되면, member 객체를 반환해라" (아이디는 존재함)
        given(memberRepository.findByEmail(email)).willReturn(member);
        // "passwordEncoder.matches()가 호출되면, false를 반환해라" (비밀번호가 틀렸다고 설정)
        given(passwordEncoder.matches(rawPassword, encodedPassword)).willReturn(false);

        // when (실행)
        Member result = memberService.login(email, rawPassword);

        // then (검증)
        assertThat(result).isNull(); // 결과가 null이어야 함 (로그인 실패)
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 존재하지 않는 아이디")
    void login_fail_member_not_found() {
        // given (준비)
        String email = "nonexistent@test.com"; // 존재하지 않는 이메일
        String rawPassword = "password123";

        // "memberRepository.findByEmail(email)이 호출되면, null을 반환해라" (회원이 없다고 설정)
        given(memberRepository.findByEmail(email)).willReturn(null);

        // when (실행)
        Member result = memberService.login(email, rawPassword);

        // then (검증)
        assertThat(result).isNull(); // 결과가 null이어야 함 (로그인 실패)
    }
}