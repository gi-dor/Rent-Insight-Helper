package com.realestate.rent_insight.controller;

import com.realestate.rent_insight.common.exception.DuplicateFieldException;
import com.realestate.rent_insight.dto.MemberDTO;
import com.realestate.rent_insight.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 단순 회원가입 폼 보여주기
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

        // 검증 결과 오류잡기
        if (bindingResult.hasErrors()) {
            // 이런거 쓰면 병목현상?? 일어난다고 뚜드러 맞는다고함
            // 성능저하 , 로그 관리 불가능 하다고함 , 휘발성이라 과거 에러기록 못찾음
            // System.out.println("검증 오류 발생: " + bindingResult.getAllErrors());
            log.warn("검증 오류 발생 ! target : {} , error : {}",
                    bindingResult.getObjectName(),
                    bindingResult.getAllErrors());

            // 검증 잡히면 회원가입 폼으로 다시
            return "members/joinForm";
        }
        try {
            memberService.join(
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
                    e.getField().equals("nickname") ? memberDTO.getNickname() : memberDTO.getEmail());

            bindingResult.rejectValue(e.getField(), "duplicate", e.getMessage());
            return "members/joinForm";

        }

        // 검증 통과하면 회원가입 고고
        memberService.join(
                memberDTO.getEmail(),
                memberDTO.getPassword(),
                memberDTO.getName() ,
                memberDTO.getNickname()
        );
        return "redirect:/";
    }
}
