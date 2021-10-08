package com.sparta.blog.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private Long id;        //글번호
    private String title;   //제목
    private String name;    //작성자명
    private String content; //내용
}
