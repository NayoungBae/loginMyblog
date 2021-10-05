package com.sparta.blog.repository;

import com.sparta.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    Optional<User> findByKakaoId(Long kakaoId);
    Optional<User> findByEmail(String mail); //카카오이메일 또는 회원가입 시 입력했던 이메일
}
