package com.sparta.blog.service;

import com.sparta.blog.models.SignupRequestDto;
import com.sparta.blog.models.User;
import com.sparta.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    //암호화
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public User registerUser(SignupRequestDto requestDto) {
        System.out.println("public User registerUser(SignupRequestDto requestDto) >>>>>>>>>>>");
        System.out.println("requestDto: " + requestDto.getName() + " " + requestDto.getNickname() + " " +
                                            requestDto.getEmail() + " " + requestDto.getPassword());
        //닉네임 중복 확인
        String nickname = requestDto.getNickname();
        Optional<User> isDuplicatedNickname = userRepository.findByNickname(nickname);
        //중복된 닉네임이 존재할 경우
        if(isDuplicatedNickname.isPresent()) {
            throw new NullPointerException("중복된 닉네임이 존재합니다.");

        }
        //중복된 닉네임이 존재하지 않을 경우
        String name = requestDto.getName();     //이름
        String password = passwordEncoder.encode(requestDto.getPassword()); //암호화된 비밀번호
        String email = requestDto.getEmail(); //이메일

        User user = new User(nickname, name, email, password);
        userRepository.save(user);
        System.out.println("public User registerUser(SignupRequestDto requestDto) 끝 >>>>>>>>>>>");
        return user;

    }

    // 회원가입 시, 유효성 체크
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }
}
