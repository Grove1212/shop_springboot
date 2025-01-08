package com.stella223.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    @GetMapping("/signUp")
    String signUp(){
        return "signUp.html";
    }

    @PostMapping("/saveUsrData")
    String saveUsrData(@RequestParam String userName, String password, String displayName){
        try {
            memberService.saveMember(userName, password, displayName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/list";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth){
        CustomUser result = (CustomUser) auth.getPrincipal();
        return "mypage.html";
    }

    @GetMapping("/register")
    public String register(Authentication auth){
        if(auth.isAuthenticated()){
            return "redirect:/list";
        }
        return "register.html";
    }

}
