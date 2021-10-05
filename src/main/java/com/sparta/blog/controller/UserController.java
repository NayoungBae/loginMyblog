package com.sparta.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.blog.models.SignupRequestDto;
import com.sparta.blog.service.KakaoUserService;
import com.sparta.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "sign_in_up";
    }

     //회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto){
        try {
            userService.registerUser(requestDto);
        } catch (Exception e) {
            System.out.println(e);
            return "redirect:/user/login?fail";
        }
        return "redirect:/user/login";
    }

    // 회원 가입 요청 처리
//    @PostMapping("/user/signup")
//    public String registerUser(@RequestBody @Valid SignupRequestDto requestDto,
//                               Errors errors, Model model) throws Exception{
//        userService.registerUser(requestDto);
//        if(errors.hasErrors()) {
//            //회원가입 실패시, 입력 데이터를 유지지
//           model.addAttribute("signupRequestDto", requestDto);
//
//            //유효성 통과 못한 필드와 메시지를 핸들링
//        } catch (Exception e) {
//            System.out.println(e);
//            return "redirect:/user/login?fail";
//        }
//        return "redirect:/user/login";
//    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakoLogin(code);
        return "redirect:/";
    }
}
