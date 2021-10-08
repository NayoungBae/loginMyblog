package com.sparta.blog.service;

import com.sparta.blog.models.Post;
import com.sparta.blog.models.PostRequestDto;
import com.sparta.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostRequestDto requestDto, Long userId) {
        Post post = new Post(requestDto, userId);
        return postRepository.save(post);
    }

    @Transactional
    public Long update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("PostService) DB에 ID값이 없습니다.")
        );
        post.update(requestDto);
        return post.getId();
    }

    public List<Post> getPosts() {
        System.out.println("public Page<Post> getPosts() 함수 시작 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");


        //게시물 데이터와 페이지 관련 데이터 담는 변수
        List<Post> result = postRepository.findAllByOrderByModifiedAtDesc();

        System.out.println("public Page<Post> getPosts() 함수 끝 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return result;
    }

    public Post showDetail(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
