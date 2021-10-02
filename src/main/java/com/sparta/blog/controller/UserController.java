package com.sparta.blog.controller;

import com.sparta.blog.models.SignupRequestDto;
import com.sparta.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    //private final KakaoUserService kakaoUserService;

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "sign_in_up";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto) {
        try {
            userService.registerUser(requestDto);
        } catch (Exception e) {
            System.out.println(e);
            return "redirect:/user/login?fail";
        }
        return "redirect:/user/login";
    }
//
//    @GetMapping("/user/kakao/callback")
//    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
//        kakaoUserService.kakoLogin(code);
//        return "redirect:/";
//    }
}
