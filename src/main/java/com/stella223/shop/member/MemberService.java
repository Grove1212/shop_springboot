package com.stella223.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveMember(String userName, String password, String displayName) throws Exception {
        var result = memberRepository.findByUsername(userName);
        if(result.isPresent()){
            throw new Exception("존재하는 아이디");
        }

        if(password.length() <3){
            throw new Exception("너무 짧음");
        }
        var hashedPassword = passwordEncoder.encode(password);
        Member member = new Member(userName, hashedPassword, displayName);
        memberRepository.save(member);
    }
}
