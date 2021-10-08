package com.sparta.blog.service;

import com.sparta.blog.models.Comment;
import com.sparta.blog.models.CommentRequestDto;
import com.sparta.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getComments(Long postId) {
        return commentRepository.findAllByPostIdOrderByModifiedAtDesc(postId);
    }

    @Transactional
    public Comment createComment(CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto);
        return commentRepository.save(comment);
    }

    @Transactional
    public Long updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("CommentService) DB에 아이디값이 없습니다.")
        );
        comment.update(requestDto);
        return comment.getId();
    }

    @Transactional
    public Long deleteComment(Long id) {
        commentRepository.deleteById(id);
        return id;
    }
}
