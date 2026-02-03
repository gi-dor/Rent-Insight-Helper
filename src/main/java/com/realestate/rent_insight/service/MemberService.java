package com.realestate.rent_insight.service;

import com.realestate.rent_insight.domain.constant.MemberRole;
import com.realestate.rent_insight.domain.entity.Member;
import com.realestate.rent_insight.domain.repository.MemberRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(String email, String password, String name ,String nickname) {
        // 회원 가입 전에 이메일 중복 체크
        validateDuplicateMember(email);

        // 기본적으로 회원 가입시 시작은 일반 사용자(USER) 권한 부여
//        Member member = new Member(email, password, name, nickname, null, MemberRole.USER, null, null);

        //  member 객체 생성할때 createAt은 null로 들어감
        // repository.save 호출
        // DB 넣기 전에 @EntityListeners 가 작동해서 저장할때 현재 시간을 주입해줌
        Member member = Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .memberRole(MemberRole.USER)
                .build();

        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    private void validateDuplicateMember(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
