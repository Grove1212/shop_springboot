package com.stella223.shop;
//스프링부트 문법

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class BasicController {
    @GetMapping("/")
    String hello() {
        return "index.html";
        //기본경로가 static 폴더
        //메인페이지 접속하면 이거 보내주세요
    }

    @GetMapping("/about")
    @ResponseBody
    String introduce() {
        return "stella 쇼핑몰은 질 좋은 꿈나라를 선물해드립니다.";//메인페이지 접속하면 이거 보내주세요
    }

    @GetMapping("/date")
    @ResponseBody
    String date() {
        LocalDateTime now = LocalDateTime.now();
        String nowStr = ""+now;

        return nowStr;//메인페이지 접속하면 이거 보내주세요
    }
}
