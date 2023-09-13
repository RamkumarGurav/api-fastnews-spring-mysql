package com.ram.fastnewsspringmysql.service;

import com.ram.fastnewsspringmysql.collection.Comment;
import com.ram.fastnewsspringmysql.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;



    @Override
    public Comment createComment(Comment filteredComment) {
        return commentRepository.save(filteredComment);
    }

    @Override
    public Comment updateComment(Comment filteredComment) {
        filteredComment.setUpdated(true);
        return commentRepository.save(filteredComment) ;
    }

    @Override
    public Page<Comment> searchCommentsByKeywordAndPostIdAndUserIdAndCommentId(String keyword, boolean active, boolean deleted, Long postId, Long userId, Long commentId, Pageable pageable) {
        return commentRepository.searchCommentsByKeywordAndPostIdAndUserIdAndCommentId(keyword,active,deleted,postId,userId,commentId,pageable);
    }


    @Override
    public Page<Comment> getAllCommentsOnThisPost(Long postId, Pageable pageable) {
        return commentRepository.findAllByPostId(postId,pageable);
    }

    @Override
    public Page<Comment> getAllCommentsByAdmin(Long postId, Pageable pageable) {
        return commentRepository.findAllByPostId(postId,pageable);
    }


    @Override
    public Optional<Comment> getSingleCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Optional<Comment> getSingleCommentByIdAndUserId(Long id, Long userId) {
        return commentRepository.findByCommentIdAndUser_UserId(id,userId);
    }

    @Override
    public Optional<Comment> getSingleCommentByIdAndPostId(Long id, Long postId) {
        return commentRepository.findByCommentIdAndPostId(id,postId);
    }

    @Override
    public Optional<Comment> getSingleCommentByIdAndPostIdAndUserId(Long id, Long postId, Long userId) {
        return commentRepository.findByCommentIdAndPostIdAndUser_UserId(id,postId,userId);
    }

    @Override
    public void deleteCommentByIdAndPostIdandUserId(Long id, Long postId, Long userId) {
        commentRepository.deleteByCommentIdAndPostIdAndUser_UserId(id,postId,userId);
    }

    @Override
    public void deleteMySingleCommentOnThisPost(Long id, Long postId, Long userId) {

        commentRepository.deleteByCommentIdAndPostIdAndUser_UserId(id,postId,userId);

    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);

    }

    @Override
    public void deleteAllMyComments(Long userId) {
commentRepository.deleteAllByUser_UserId(userId);
    }

    @Override
    public void deleteAllMyCommentsOnThisPost(Long userId, Long postId) {
commentRepository.deleteAllByPostIdAndUser_UserId(postId,userId);
    }




    @Override
    public void partiallyDeleteCommentById(Comment comment) {
        comment.setDeleted(true);
        comment.setUpdated(true);
        comment.setActive(false);
        commentRepository.save(comment);
    }


}
