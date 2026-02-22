package com.realestate.rent_insight.domain.repository;

import com.realestate.rent_insight.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 플리케이션이 실행될 때, 스프링이 내부적으로 해당 인터페이스를 구현한 실제 클래스를 자동으로 생성해서 메모리에 올려줌
// 이게 스프링의 Proxy 객체라는디 ??
// 인터페이스인데 implements 로 구현없이 JPARepository 상속받으면 구현체를 만들어준데 이걸 프록시 객체라고하네
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 기본 기능 외에 우리가 필요한 검색 기능을 이름만으로 만들 수 있습니다.

    // 이메일로 회원 찾기 (로그인할 때 검색해서)
    // SQL: select * from member where email = ?
    Optional<Member> findByEmail(String email);
    // Optional<Member> findByEmail(String email);
    // Optional<Member> 선언하고 데이터 없을떄 NullPointerException 방지 + 서비스 계층에서 orElseThrow() 로 예외처리 한다는데



    // 이메일 중복 체크용 (회원가입할 때 필요함)
    // SQL: select count(*) from member where email = ?
    // SQL : SELECT 1 FROM member WHERE email = ? LIMIT 1  둘중 뭐가 맞는지 모르게슴
    boolean existsByEmail(String email);

    // 닉네임 중복체크 (회원가입)
    // SQL : select count(*) from member where nickname = ?
    boolean existsByNickname(String nickname);
}
