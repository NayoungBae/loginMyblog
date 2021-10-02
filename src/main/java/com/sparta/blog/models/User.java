package com.sparta.blog.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;    //회원번호

    @Column(unique = true)
    private Long kakaoId;   //카카오 회원번호

    @Column(nullable = false, unique = true)
    private String nickname;    //닉네임

    @Column(nullable = false)
    private String name;    //이름

    @Column(nullable = false)
    private String password;    //비밀번호


    public User(String nickname, String name, String password) {
        this.nickname = nickname;
        this.name = name;
        this.password = password;
    }
}