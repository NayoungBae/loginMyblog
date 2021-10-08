package com.sparta.blog.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostResponseDto {
    private Long id;        //글번호
    private Long userId;    //사용자 고유번호
    private String title;   //제목
    private String name;    //작성자명
    private String content; //내용
}
