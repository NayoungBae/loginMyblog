package com.sparta.blog.controller;

import com.sparta.blog.models.Comment;
import com.sparta.blog.models.CommentRequestDto;
import com.sparta.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    //해당 게시물에 대한 모든 댓글
    @GetMapping("/comments/comment/{id}")
    public List<Comment> getComments(@PathVariable("id") Long postId) {
        return commentService.getComments(postId);
    }

    //게시물에 댓글 달기
    @PostMapping("/comments/comment")
    public Comment writeComment(@RequestBody CommentRequestDto requestDto) {
        System.out.println("userId: " + requestDto.getUserId() + ", postId: " + requestDto.getPostId() +
                            ", name: " + requestDto.getName() + ", comment" + requestDto.getComment());
        return commentService.createComment(requestDto);
    }

    //댓글 수정
    @PutMapping("/comments/comment/{id}")
    public Long updateComment(@PathVariable Long id,
                              @RequestBody CommentRequestDto requestDto) {
        System.out.println("id: " + id + ", userId: " + requestDto.getUserId() + ", postId: " + requestDto.getPostId() +
                ", name: " + requestDto.getName() + ", comment: " + requestDto.getComment());

        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/comments/comment/{id}")
    public Long deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return id;
    }
}
