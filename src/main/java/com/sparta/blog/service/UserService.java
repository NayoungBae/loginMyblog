package com.sparta.blog.service;

import com.sparta.blog.models.SignupRequestDto;
import com.sparta.blog.models.User;
import com.sparta.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
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
    public User registerUser(SignupRequestDto requestDto) throws Exception {
        System.out.println("public User registerUser(SignupRequestDto requestDto) >>>>>>>>>>>");
        System.out.println("requestDto: " + requestDto.getName() + " " + requestDto.getNickname() + " " +
                                            requestDto.getEmail() + " " + requestDto.getPassword());
        //닉네임 중복 확인
        String nickname = requestDto.getNickname();
        Optional<User> isDuplicatedNickname = userRepository.findByNickname(nickname);
        //중복된 닉네임이 존재할 경우
        if(isDuplicatedNickname.isPresent()) {
            throw new Exception("중복된 닉네임입니다.");
        }

        //비밀번호
        String password = passwordEncoder.encode(requestDto.getPassword()); //암호화된 비밀번호
        System.out.println("password: " + password);

        //비밀번호 일치 확인(비밀번호와 비밀번호확인 값을 matches를 통해 비교)
        //(암호화되지 않은 비밀번호, 암호화된 비밀번호)
        boolean isEqualPassword = passwordEncoder.matches(requestDto.getCheckPassword(), password);
        if(!isEqualPassword) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        //비밀번호에 아이디값이 포함되어있는지 아닌지 판단
        boolean isIncludedIdInPw = requestDto.getCheckPassword().contains(requestDto.getNickname());
        if(isIncludedIdInPw) {
            throw new Exception("비밀번호에 닉네임과 같은 값이 포함되어 있습니다.");
        }

        //이메일 중복 검사(카카오와 연동때문에 추가)
        String email = requestDto.getEmail(); //이메일
        Optional<User> isExistEmaiil = userRepository.findByEmail(email);
        System.out.println("email: " + email);
        System.out.println("isExistEmaiil: " + isExistEmaiil);
        if(isExistEmaiil.isPresent()) {
            throw new Exception("중복된 이메일입니다.");
        }


        //중복된 닉네임이 존재하지 않을 경우
        String name = requestDto.getName();     //이름

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
