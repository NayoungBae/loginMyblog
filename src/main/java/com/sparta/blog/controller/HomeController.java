package com.sparta.blog.controller;


import com.sparta.blog.models.Post;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final PostService postService;

    //로그인 안 했을 때 타는 로직
    //로그인 했을 때 WebSecurityConfig의 defaultSuccessUrl("/")를 타는데
    //이것은 스프링 시큐리티가 알아서 index라는 파일을 찾아감
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) 시작 >>>>");
        try {
            if(userDetails != null) {
                System.out.println("userId: " + userDetails.getUser().getId());
                model.addAttribute("name", userDetails.getUser().getName()); //사용자 이름
                model.addAttribute("userId", userDetails.getUser().getId()); //USER테이블 고유번호
            }

            List<Post> postList = postService.getPosts();
            model.addAttribute("postList", postList);

        } catch(Exception e) {
            System.out.println("PostController) home 함수에서 에러: " + e.getMessage());
        }

        System.out.println("public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) 끝 >>>>");
        return "index";
    }

}