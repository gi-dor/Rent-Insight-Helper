package com.realestate.rent_insight.domain.repository;

import com.realestate.rent_insight.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 기본 기능 외에 우리가 필요한 검색 기능을 이름만으로 만들 수 있습니다.

    // 이메일로 회원 찾기 (로그인할 때 검색해서)
    // SQL: select * from member where email = ?
    Optional<Member> findByEmail(String email);

    // 이메일 중복 체크용 (회원가입할 때 필요함)
    // SQL: select count(*) from member where email = ?
    boolean existsByEmail(String email);

    // 닉네임 중복체크 (회원가입)
    boolean existsByNickname(String nickname);
}
