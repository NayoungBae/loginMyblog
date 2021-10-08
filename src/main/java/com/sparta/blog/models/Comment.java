package com.sparta.blog.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;            //댓글번호

    @Column(nullable = false)
    private Long postId;        //게시물번호

    @Column(nullable = false)
    private Long userId;        //사용자 번호

    @Column(nullable = false)
    private String name;          //사용자 이름

    @Column
    private String comment;     //댓글내용

    public Comment(CommentRequestDto requestDto) {
        //this.id = requestDto.getId();
        this.postId = requestDto.getPostId();
        this.userId = requestDto.getUserId();
        this.name = requestDto.getName();
        this.comment = requestDto.getComment();
    }

    public void update(CommentRequestDto requestDto) {
        this.postId = requestDto.getPostId();
        this.userId = requestDto.getUserId();
        this.name = requestDto.getName();
        this.comment = requestDto.getComment();
    }
}
