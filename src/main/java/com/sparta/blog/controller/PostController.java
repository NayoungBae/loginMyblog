package com.sparta.blog.controller;

import com.sparta.blog.models.Post;
import com.sparta.blog.models.PostRequestDto;
import com.sparta.blog.repository.PostRepository;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
//@RestController
@Controller
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    //글쓰기 버튼 눌렀을 때 DB에 Insert
    //@Secured(value = UserRoleEnum.Authority.USER)
    @PostMapping("/api/posts")
    public Post writePost(@RequestBody PostRequestDto requestDto,
                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("public Post writePost() 함수 시작 >>>>>>>>>>>>>>>>>>>>>>>>>");
        //로그인 되어 있는 회원 테이블의 ID값
        Long userId = userDetails.getUser().getId();
        System.out.println("userId: " + userId);

        //DB에 글 데이터 Insert
        Post post = postService.createProduct(requestDto, userId);

        System.out.println("public Post writePost() 함수 끝 >>>>>>>>>>>>>>>>>>>>>>>>>");
        return post;
    }

    //게시물 전체 조회/검색 & 페이지 관련 데이터 조회
    //계정에 따라 조회되는 데이터가 달라야함!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //@Secured(value = UserRoleEnum.Authority.USER) //모든 사용자가 쓴 글을 모두 보는건 관리자 기능일텐데???????????????!!!!!!
    @GetMapping("/api/posts")
    public Page<Post> getPosts(@RequestParam(value="title", required = false, defaultValue = "") String title,
                                  @RequestParam(value="name", required = false, defaultValue = "") String name,
                                  @RequestParam(value="content", required = false, defaultValue = "") String content,
                                  @RequestParam(value="page" , required = false, defaultValue = "0") String page,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("PostController) public Page<Post> getPosts() 함수 시작 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        //로그인 되어 있는 회원 테이블의 ID(고유번호)
        Long userId = userDetails.getUser().getId();
        System.out.println("userId: " + userId);

        Page<Post> result = postService.getPosts(title, name, content, page, userId);

        System.out.println("PostController) public Page<Post> getPosts() 함수 끝 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return result;
    }

    //게시물 상세내용 조회
    //@Secured(value = UserRoleEnum.Authority.USER)
    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Long id,
                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("PostController) public Post getPost() 함수 시작 >>>>>>>>>>>>>>>>>>>>");

        //로그인 되어 있는 회원 테이블의 ID(고유번호)
        Long userId = userDetails.getUser().getId();
        System.out.println("id: " + id + ", userId: " + userId);

        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("PostController) DB에 ID값이 없습니다.")
        );
        System.out.println("PostController) public Post getPost() 함수 끝 >>>>>>>>>>>>>>>>>>>>");
        return post;
    }

    //게시물 수정
    //@Secured(value = UserRoleEnum.Authority.USER)
    @PutMapping("/api/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    //게시물 삭제
    //@Secured(value = UserRoleEnum.Authority.USER)
    @DeleteMapping("/api/posts/{id}")
    public Long deletePostById(@PathVariable Long id) {
        postService.deletePost(id);
        return id;
    }

}
