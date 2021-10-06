package com.sparta.blog.controller;

import com.sparta.blog.security.UserDetailsImpl;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
//    @GetMapping("/")
//    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        model.addAttribute("nickname", userDetails.getUsername());
//        return "index";
//    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            String nickname = userDetails.getUsername();
            System.out.println("nickname: " + nickname);
            if(nickname != null) {
                model.addAttribute("nickname", nickname);
            }
        } catch(Exception e) {
            System.out.println("에러:" + e.getMessage());
        }
        return "index";
    }
}