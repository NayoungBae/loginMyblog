package com.sparta.blog.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String name;
    private String nickname;
    private String password;
}
