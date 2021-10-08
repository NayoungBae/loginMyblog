package com.sparta.blog.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    //private Long id;        //댓글번호
    private Long postId;    //게시물번호
    private Long userId;    //사용자 번호
    private String name;      //사용자 이름
    private String comment; //댓글내용

}
