package com.realestate.rent_insight.config;

import com.realestate.rent_insight.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
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


    @Bean
    public LoginFailHandler loginFailHandler() {
        return new LoginFailHandler();
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
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/members/login", "/members/join").permitAll()
                        .requestMatchers("/rent/search").permitAll()
                        .requestMatchers("/members/mypage").authenticated()
                        .anyRequest().authenticated()
                )
                // 폼 기반 로그인 설정
                .formLogin(form -> form
                        .loginPage("/members/login")
                        .loginProcessingUrl("/members/login")
                        .failureHandler(loginFailHandler())
                        .usernameParameter("email")
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 항상 메인 페이지로 이동
                        .permitAll() // 로그인 페이지 자체는 누구나 접근 가능
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/members/logout") // 로그아웃을 처리할 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트될 URL
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                );

        return http.build();
    }



}
