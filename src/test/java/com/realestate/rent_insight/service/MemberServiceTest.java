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

    }

    @Test
    @DisplayName("로그인 실패 테스트 - 비밀번호 틀림")
    void login_fail_wrong_password() {
        // given (준비)


        // when (실행)


        // then (검증)

    }

    @Test
    @DisplayName("로그인 실패 테스트 - 존재하지 않는 아이디")
    void login_fail_member_not_found() {
        // given (준비)

    }
}