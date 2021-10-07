package com.sparta.blog.controller;

import com.sparta.blog.models.Post;
import com.sparta.blog.models.PostRequestDto;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    //로그인 안 했을 때 타는 로직
    //로그인 했을 때 WebSecurityConfig의 defaultSuccessUrl("/")를 타는데
    //이것은 스프링 시큐리티가 알아서 index라는 파일을 찾아감
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) 시작 >>>>");
        try {
            if(userDetails != null) {
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

    @GetMapping("posts/post")
    public String writePost(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails != null) {
            model.addAttribute("userId", userDetails.getUser().getId());
            model.addAttribute("name", userDetails.getUser().getName());
        }
        return "write_post";
    }

    @PostMapping("/posts/post")
    public String insertPost(PostRequestDto requestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("public Long insertPost(@PathVariable Long id , PostRequestDto requestDto) 시작 >>>>>>>>>");
        System.out.println("title: " + requestDto.getTitle() + ", name: " + requestDto.getName() +
                                                                        ", content:" + requestDto.getContent());
        //로그인 되어 있는 회원 테이블의 ID값
        Long userId = userDetails.getUser().getId();
        System.out.println("userId: " + userId);
        Post post = postService.createPost(requestDto, userId);
        return "redirect:/";
    }

    //상세보기
    @GetMapping("/posts/post/{id}")
    public String postDetail(@PathVariable Long id, Model model,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("public Optional<Post> postDetail() 시작 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Post post = postService.showDetail(id);

        model.addAttribute("userId", userDetails.getUser().getId()); //로그인 되어 있는 회원 테이블의 ID값
        model.addAttribute("name", userDetails.getUser().getName()); //사용자 이름
        model.addAttribute("post", post); //게시물 데이터

        System.out.println("public Optional<Post> postDetail() 끝 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return "post_detail";
    }

    //수정하기(Update)
    @PutMapping("/posts/post/{id}")
    public String updatePost(@PathVariable Long id, PostRequestDto requestDto, Model model,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("public String updatePost() 시작 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        postService.update(id, requestDto);

        //model.addAttribute("userId", userDetails.getUser().getId()); //로그인 되어 있는 회원 테이블의 ID값
        //model.addAttribute("name", userDetails.getUser().getName()); //사용자 이름


        System.out.println("public String updatePost() 시작 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return "redirect:/posts/post/{id}";
    }

}
