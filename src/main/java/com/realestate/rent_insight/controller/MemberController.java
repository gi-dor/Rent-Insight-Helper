package com.realestate.rent_insight.controller;

import com.realestate.rent_insight.common.exception.DuplicateFieldException;
import com.realestate.rent_insight.domain.entity.Member;
import com.realestate.rent_insight.dto.MemberDTO;
import com.realestate.rent_insight.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 단순 회원가입 폼 보여주기 - @ModelAttribute 생략가능 스프링에서 객체 일경우 자동으로 박아줌 굿
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("memberDTO") MemberDTO memberDTO) {
        return "members/joinForm";
    }



    /**
     * @Valid - MemberDTO를 서비스로직에 넣기 전에 설정된 제약조건을 검사함 @NotBlank , @Size 등등
     * BindingResult - 검사결과 보고서 - @Valid로 검증된 결과가 담기는 인터페이스 , name에 한글이 아니라면 에러 정보를 BindingResult에 담음
     *
     * @Valid MemberDTO dto, BindingResult result 검증할 대상객체 바로 뒤에 위치 해야함
     * @Valid MemberDTO dto, Model model, BindingResult result 작동안할수 있음
     * */
    // 가입하기 버튼 누를떄 작동
    // 회원정보 서버로 전달 - DB저장 -> 끝나면 메인페이지로
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute MemberDTO memberDTO , BindingResult bindingResult) {

        // DTO로 사용자의 입력값 오류잡기
        if (bindingResult.hasErrors()) {
            log.warn("회원가입 입력값 검증 오류 발생 ! target : {} , error : {}",
                    bindingResult.getObjectName(),
                    bindingResult.getAllErrors());

            // 검증 잡히면 회원가입 폼으로 다시
            return "members/joinForm";
        }
        // 실제 비즈니스 로직
        try {
            memberService.join(     // join 메서드내에 이미 중복검사 코드 박아져있음
                    memberDTO.getEmail(),
                    memberDTO.getPassword(),
                    memberDTO.getName(),
                    memberDTO.getNickname()
            );
        } catch (DuplicateFieldException e) {
            // 1. 중복된 값이 무엇인지 추출 (이메일 혹은 닉네임)
            String duplicateValue = e.getField().equals("nickname")
                    ? memberDTO.getNickname()
                    : memberDTO.getEmail();

            // 2. 로그 남기기
            log.warn("[FIELD_DUPLICATE_ERROR] Field: {}, Message: {}, Value: {}",
                    e.getField(), e.getMessage(),
                    e.getField().equals("nickname") ?
                            memberDTO.getNickname() : memberDTO.getEmail());

            bindingResult.rejectValue(e.getField(), "duplicate", e.getMessage());
            return "members/joinForm";

        }

        log.info("회원가입 완료 ,  이름 : {} , 이메일(아이디) {}",memberDTO.getName(),memberDTO.getEmail());
        return "redirect:/members/joinComplete";    // 새로고침 -> 중복 가입 방지
    }

    // 단순 회원가입 폼 보여주기
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("memberDTO") MemberDTO memberDTO){
        return "login"; // templates/login.html
    }


    // 시큐리티로 인해 폐업
//    @PostMapping("/login")
//    public String loginComplete(@Valid @ModelAttribute MemberDTO memberDTO,BindingResult bindingResult,HttpServletRequest request) {
//        // DTO에서 정의한 입력값 검증하기
//        if (bindingResult.hasErrors()) {
//            return "login"; // login.html
//        }
//
//        // memberService에서 login 메서드 호출
//        Member loginMember = memberService.login(memberDTO.getEmail(), memberDTO.getPassword());
//
//
//        // 로그인 실패 처리 - 아이디 공백 , 비밀번호 틀릴경우
//        if (loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//            log.warn("로그인 실패 : {} ,{}",memberDTO.getEmail(),memberDTO.getPassword());
//            return "login";
//        }
//
//        // 로그인 성공 (세션 생성)
//        HttpSession session = request.getSession();
//        session.setAttribute("loginMember", loginMember);
//
//        return "redirect:/";
//    }

    // 시큐리티로 인해 폐업
//    // 로그아웃
//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//        return "redirect:/";
//
//    }
}
