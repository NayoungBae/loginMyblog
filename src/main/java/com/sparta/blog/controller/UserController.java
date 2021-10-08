package com.sparta.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.blog.models.SignupRequestDto;
import com.sparta.blog.models.User;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.KakaoUserService;
import com.sparta.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@AllArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("public String login() 함수 시작 >>>>>>>>>>>>>>>>>>>>>>>>>");
        isLogined(model, userDetails);
        System.out.println("isLogined(model, userDetails)");
        System.out.println("public String login() 함수 끝 >>>>>>>>>>>>>>>>>>>>>>>>>");
        return "login";
    }

//    // 회원 로그인 페이지
//    @GetMapping("/user/login")
//    public String login(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        try {
//            String nickname = userDetails.getUsername();
//            System.out.println("nickname: " + nickname);
//            if(nickname != null) {
//                model.addAttribute("nickname", nickname);
//            }
//        } catch(Exception e) {
//            System.out.println("에러:" + e.getMessage());
//            model.addAttribute("errorMessage", e.getMessage());
//        }
//        return "login";
//    }

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String signup(SignupRequestDto signupRequestDto, Model model,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("public String signup() 함수 시작 >>>>>>>>>>>>>>>>>>>>>>>>>");
        isLogined(model, userDetails);
        System.out.println("isLogined(model, userDetails)");
        System.out.println("public String signup() 함수 끝 >>>>>>>>>>>>>>>>>>>>>>>>>");
        return "sign_up";
    }

//    public void isLogined(Model model, UserDetailsImpl userDetails) {
//        try {
//            String nickname = userDetails.getUsername();
//            System.out.println("nickname: " + nickname);
//            if(nickname != null) {
//                model.addAttribute("isLoginedMessage", "이미 로그인이 되어있습니다.");
//            }
//        } catch(Exception e) {
//            System.out.println("UserController) 에러:" + e.getMessage());
//        }
//    }

    public void isLogined(Model model, UserDetailsImpl userDetails) {
        try {
            String nickname = userDetails.getUser().getNickname();
            System.out.println("nickname: " + nickname);
            if(nickname != null) {
                System.out.println("if(nickname != null) {");
                model.addAttribute("alreadyLoginedMessage", "이미 로그인이 되어있습니다.");
            }
        } catch(Exception e) {
            System.out.println("UserController) 에러:" + e.getMessage());
        }
    }

//     //회원 가입 요청 처리
//    @PostMapping("/user/signup")
//    public String registerUser(SignupRequestDto requestDto){
//        try {
//            userService.registerUser(requestDto);
//        } catch (Exception e) {
//            System.out.println(e);
//            return "redirect:/user/login?fail";
//        }
//        return "redirect:/user/login";
//    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid SignupRequestDto signupRequestDto,
                               Errors errors, Model model) throws Exception{
        System.out.println("public String registerUser(@Valid SignupRequestDto signupRequestDto, " +
                                                                                    "Errors errors, Model model) ");
        System.out.println("requestDto: " + signupRequestDto + ", errors:" + errors + ", model: " + model);

        if(errors.hasErrors()) {
            System.out.println("if(errors.hasErrors()) {");
            //회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("signupRequestDto", signupRequestDto);

            //유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for(String key: validatorResult.keySet()) {
                System.out.println("key: " + key + ", validatorResult.get(key): " + validatorResult.get(key));
                model.addAttribute(key, validatorResult.get(key));
            }
            return "sign_up";
        }

        try {
            //유효성 검사 & 회원가입
            User user = userService.registerUser(signupRequestDto);
        } catch(Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "sign_up";
        }
        return "redirect:/user/login";
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakoLogin(code);
        return "redirect:/";
    }
}
