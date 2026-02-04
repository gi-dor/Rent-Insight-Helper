package com.realestate.rent_insight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Configuration: 이 클래스가 Spring의 설정 정보를 담고 있는 클래스임을 나타냅니다.
 *                 Spring 컨테이너는 이 클래스를 스캔하여 여기에 정의된 Bean들을 관리합니다.
 * @EnableWebSecurity: Spring Security의 웹 보안 관련 기능들을 활성화합니다.
 *                     이 어노테이션을 추가하는 순간, 기본적인 웹 보안 설정이 자동으로 적용됩니다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * @Bean: 이 메서드가 반환하는 객체(PasswordEncoder)를 Spring 컨테이너가 관리하는 Bean으로 등록하라는 의미입니다.
     *      이렇게 등록된 Bean은 프로젝트 전역에서 의존성 주입(DI)을 통해 사용할 수 있습니다.
     *
     * @return PasswordEncoder: 비밀번호 암호화를 위한 인터페이스입니다.
     *         우리는 BCrypt 알고리즘을 사용하는 구현체인 BCryptPasswordEncoder를 반환합니다.
     *          BCrypt 암호화 도구를 생성해서 Spring에 등록.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @Bean: Spring Security의 핵심 보안 설정을 구성하는 SecurityFilterChain을 Bean으로 등록합니다.
     *
     * @param http HttpSecurity 객체. HTTP 요청에 대한 보안을 설정하는 데 사용되는 핵심 객체입니다.
     * @return SecurityFilterChain: HttpSecurity 설정을 기반으로 생성된 보안 필터 체인입니다.
     * @throws Exception 설정 과정에서 발생할 수 있는 예외를 처리합니다.
     *
     * 모든 페이지(/**)에 대한 접근을 허용하도록 임시 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http 객체를 사용하여 메서드 체이닝 방식으로 보안 설정을 구성합니다.
        http
                // 1. CSRF(Cross-Site Request Forgery) 공격 방어 설정
                // 람다식을 사용하여 CsrfConfigurer를 커스터마이징합니다.
                // csrf -> csrf.disable()는 CSRF 보호 기능을 비활성화하겠다는 의미입니다.
                // 개발 초기 단계나 API 서버 등 특정 상황에서는 편의를 위해 비활성화하지만,
                // 실제 웹 서비스에서는 보안을 위해 활성화하고 적절히 설정하는 것이 원칙입니다.
                .csrf(csrf -> csrf.disable())

                // 2. HTTP 요청에 대한 인가(Authorization) 규칙 설정
                // authorizeHttpRequests는 요청 경로(URL)별로 접근 권한을 설정하는 시작점입니다.
                .authorizeHttpRequests(authorize -> authorize
                        // requestMatchers("/**")는 모든 URL 패턴("/**")에 매칭되는 요청을 의미합니다.
                        // .permitAll()은 해당 요청에 대해 모든 사용자(인증 여부와 관계없이)의 접근을 허용한다는 의미입니다.
                        // 이 설정을 통해, 우리는 우선 모든 페이지에 자유롭게 접근할 수 있게 하여 개발 편의성을 높입니다.
                        // 추후 "마이페이지" 등 특정 페이지는 .authenticated() (인증된 사용자만 허용) 등으로 변경할 수 있습니다.
                        .requestMatchers("/**").permitAll()
                );

        // 위에서 구성한 모든 설정을 바탕으로 SecurityFilterChain 객체를 생성하여 반환합니다.
        // 이 반환된 객체를 Spring Security가 사용하여 실제 보안 필터로 적용합니다.
        return http.build();
    }


}
