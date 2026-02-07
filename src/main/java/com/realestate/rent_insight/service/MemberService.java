package com.realestate.rent_insight.service;

import com.realestate.rent_insight.common.exception.DuplicateFieldException;
import com.realestate.rent_insight.domain.constant.MemberRole;
import com.realestate.rent_insight.domain.entity.Member;
import com.realestate.rent_insight.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // final이 붙은 필드(memberRepository, passwordEncoder)를 위한 생성자를 자동으로 만듬
@Transactional(readOnly = true) // 기본적으로 모든 메서드는 읽기 전용으로 설정하여 성능을 최적화
public class MemberService {

    // 데이터베이스와 대화하기 위한 MemberRepository(JPA)
    private final MemberRepository memberRepository;
    // 비밀번호를 암호화하기 위한 도구 (SecurityConfig에 만들어둠)
    private final PasswordEncoder passwordEncoder;


    // =================================================================================================
    // [신규] 회원가입 - 비밀번호 암호화 적용
    // =================================================================================================
    /**
     * 회원가입을 처리하는 새로운 메서드입니다. 비밀번호를 암호화해서 저장합니다.
     *
     * @param email 사용자가 회원가입 폼에 입력한 이메일 주소
     * @param password 사용자가 입력한 비밀번호 (아직 암호화되지 않은 날것의 비밀번호)
     * @param name 사용자의 이름
     * @param nickname 사용자의 별명
     * @return 데이터베이스에 저장된 회원의 고유 ID 번호
     */
    @Transactional // 이 메서드는 데이터베이스에 정보를 저장(쓰기)하므로, @Transactional을 붙여서 쓰기 모드로 설정
    public Long join(String email, String password, String name ,String nickname) {
        // 혹시 이미 가입된 이메일이나 닉네임인지 확인합니다. (중복 체크)
        validateDuplicateEmail(email);
        validateDuplicateNickName(nickname);

        // Member(회원) 객체를 만듭니다.
        Member member = Member.builder()
                .email(email)
                // 사용자가 입력한 비밀번호를 그대로 저장하면 해킹 시 위험
                // passwordEncoder.encode() 사용
                // 비밀번호를 아무도 알아볼 수 없는 암호문으로 바꿉니다. (예: $2a$10$...)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .memberRole(MemberRole.USER) // 처음 가입하는 사람은 모두 일반 사용자(USER)
                .build();

        // 완성된 회원 정보를 데이터베이스에 저장(save)합니다.
        Member savedMember = memberRepository.save(member);

        // 데이터베이스에 저장된 회원의 ID 번호를 알려줍니다.
        return savedMember.getId();
    }


    /*
    // =================================================================================================
    // [기존 코드] 암호화가 적용되지 않았던 옛날 회원가입 메서드 (이제 사용하지 않음)
    // =================================================================================================
    @Transactional
    public Long join(String email, String password, String name ,String nickname) {
        validateDuplicateEmail(email);
        validateDuplicateNickName(nickname);

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
    */


    // =================================================================================================
    // [신규] 로그인 - 암호화된 비밀번호 비교
    // =================================================================================================
    /**
     * 로그인을 처리하는 새로운 메서드입니다. 암호화된 비밀번호를 안전하게 비교합니다.
     *
     * @param email 사용자가 로그인 폼에 입력한 이메일
     * @param password 사용자가 입력한 비밀번호 (날것의 비밀번호)
     * @return 로그인에 성공하면 회원(Member) 정보를 통째로 반환하고, 실패하면 null을 반환합니다.
     */
    public Member login(String email, String password) {
        // 사용자가 입력한 이메일로 데이터베이스에서 회원을 찾음
        Member member =  memberRepository.findByEmail(email);

        // 만약 그런 이메일을 가진 회원이 없다면, 당연히 로그인 실패이므로 null을 반환하고 끝냄
        if(member == null) {
            return null;
        }

        // 데이터베이스에 저장된 암호문(member.getPassword())과 사용자가 방금 입력한 날것의 비밀번호(password)가 서로 짝이 맞는지 확인
        // passwordEncoder.matches()가 작업을 대신 해줌 절대로 암호문을 풀어서 비교하지 않는다
        if(!passwordEncoder.matches(password, member.getPassword())) {
            // 만약 짝이 맞지 않는다면, 비밀번호가 틀린 것이므로 null을 반환하고 끝낸다
            return null;
        }

        // 이메일도 존재하고, 비밀번호도 짝이 맞는다면, 로그인 성공
        // 찾았던 회원 정보를 통째로 반환합
        return member;
    }


    /*
    // =================================================================================================
    // [기존 코드] 암호화가 적용되지 않았던 옛날 로그인 메서드 (이제 사용하지 않음)
    // =================================================================================================
    public Member login(String email, String password) {
        Member member =  memberRepository.findByEmail(email);

        if(member == null) {
            return null;
        }

        if(!member.getPassword().equals(password)) {
            return null;
        }

        return member;
    }
    */


    /**
     * 이메일 중복 체크
     */
    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateFieldException("email","이미 존재하는 회원(이메일)입니다.");
        }
    }

    /**
     * 닉네임 중복 체크
     */
    private void validateDuplicateNickName(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateFieldException("nickname","이미 존재하는 닉네임 입니다.");
        }
    }
}
