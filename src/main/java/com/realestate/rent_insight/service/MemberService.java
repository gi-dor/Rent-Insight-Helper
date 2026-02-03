package com.realestate.rent_insight.service;

import com.realestate.rent_insight.common.exception.DuplicateFieldException;
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
     * 회원가입
     * @param email
     * @param password
     * @param name
     * @param nickname
     * @return
     */
    @Transactional
    public Long join(String email, String password, String name ,String nickname) {
        // 회원 가입 전에 이메일 중복 체크
        validateDuplicateEmail(email);

        // 회원가입전에 닉네임 중복체크
        validateDuplicateNickName(nickname);


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

    /**
     * 이메일 중복 체크
     * @param email
     */
    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateFieldException("email","이미 존재하는 회원(이메일)입니다.");
        }
    }

    /**
     * 닉네임 중복 체크
     * @param nickname
     */
    private void validateDuplicateNickName(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateFieldException("nickname","이미 존재하는 닉네임 입니다.");
        }
    }
}
