package com.realestate.rent_insight.controller;

import com.realestate.rent_insight.dto.MemberDTO;
import com.realestate.rent_insight.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    // 가입하기 버튼 누를떄 작동
    // 회원정보 서버로 전달 - DB저장 -> 끝나면 메인페이지로
    @PostMapping("/join")
    public String join(@ModelAttribute MemberDTO memberDTO) {
        memberService.join(
                memberDTO.getEmail(),
                memberDTO.getPassword(),
                memberDTO.getName() ,
                memberDTO.getNickname()
        );
        return "redirect:/";
    }
}
