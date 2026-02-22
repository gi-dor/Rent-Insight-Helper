package com.realestate.rent_insight.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) // 생성일/수정일 자동 관리
@Entity // JPA가 관리하는 엔티티가됨
@Getter // getter 필드 값 가져오기
@Table(name = "member") // 테이블 이름을 명시적으로 지정
public class Member implements UserDetails { // Java에는 Member 객체있음 , DB에는 member 라는 테이블 있음 이걸 1:1 매핑해주는 Entity
    // UserDetails 상속받아서 인증객체로 사용


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 내가 건들이지 않고 DB가 알아서 증가시킴
    private Long id;        // 회원 고유 ID

    @Column(nullable = false, unique = true)        // 중복 불가
    private String email;   // 이메일 (로그인 ID)

    @Column(nullable = false)
    private String password;

    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;    // 사용자 커뮤니티나 마이정보에서 사용할 닉네임

    // 나중에 카카오 로그인을 위해 미리 만들어둠 "google", "kakao", "naver" 등
    private String provider;

    // 사용자인지 , 관리자인지 구분 - 기본 회원가입시 사용자 default값 설정
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberRole memberRole;

    // 등록일
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정일
    @LastModifiedDate
    private LocalDateTime updatedAt;


    @Builder
    public Member(String email, String password, String name, String nickname, String provider, MemberRole memberRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.provider = provider;
        this.memberRole = memberRole;
    }

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // 권한반환
    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+ memberRole.name()));
    }

    // 사용자의 id를 반환 - 고유한값
    @Override
    @NonNull
    public String getUsername() {
        return this.email;
    }

    // 계정이 만료되었는지 확인
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠금되어있는 지 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 패스워드 만료 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 여부 확인
    @Override
    public boolean isEnabled() {
        return true;
    }
}
