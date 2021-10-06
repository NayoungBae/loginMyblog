package com.sparta.blog.service;

import com.sparta.blog.models.Post;
import com.sparta.blog.models.PostRequestDto;
import com.sparta.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post createProduct(PostRequestDto requestDto, Long userId) {
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

    @Transactional
    public Page<Post> getPosts(String title, String name, String content, String page, Long userId) {
        System.out.println("public Page<Post> getPosts() 함수 시작 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("title: " + title + ", name: " + name + ", content: " + content +
                                                                    ", page: " + page + ", userId: " + userId);
        //현재 페이지 위치(문자열 -> 정수 변환)
        int currnt_page = Integer.parseInt(page);

        //게시물 데이터와 페이지 관련 데이터 담는 변수
        Page<Post> result;

        //of 함수 매개변수: (현재페이지(0부터 시작), 한 페이지 당 출력 개수)
        PageRequest pageRequest = PageRequest.of(currnt_page, 10);
        if(title.length() > 0) {
            //제목으로 검색
            result = postRepository.findByTitleContainingOrderByModifiedAtDesc(title, pageRequest);
        } else if(name.length() > 0) {
            //작성자 이름으로 검색
            result = postRepository.findByNameContainingOrderByModifiedAtDesc(name, pageRequest);
        } else if(content.length() > 0) {
            //내용으로 검색
            result = postRepository.findByContentContainingOrderByModifiedAtDesc(content, pageRequest);
        } else {
            //전체 게시물 조회
            result = postRepository.findAllByOrderByModifiedAtDesc(pageRequest);
        }
        System.out.println("public Page<Post> getPosts() 함수 끝 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return result;

    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
