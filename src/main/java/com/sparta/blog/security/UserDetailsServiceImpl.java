package com.sparta.blog.security;

import com.sparta.blog.models.User;
import com.sparta.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("public UserDetails loadUserByUsername(String username) >>>>>>>>>>>" + username + "<<<");
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));

        System.out.println("public UserDetails loadUserByUsername(String username) >>>>>>>>>>> 끝");
        return new UserDetailsImpl(user);
    }
}
