package com.ram.fastnewsspringmysql.service;

import com.ram.fastnewsspringmysql.collection.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment createComment(Comment filteredComment);

    Comment updateComment(Comment filteredComment);




    Page<Comment> searchCommentsByKeywordAndPostIdAndUserIdAndCommentId(String keyword, boolean active, boolean deleted, Long postId, Long userId, Long commentId, Pageable pageable);

    Page<Comment> getAllCommentsOnThisPost(Long postId, Pageable pageable);

    Page<Comment> getAllCommentsByAdmin(Long postId,Pageable pageable);

    Optional<Comment> getSingleCommentById(Long id);

    Optional<Comment> getSingleCommentByIdAndUserId(Long id,Long userId);

    Optional<Comment> getSingleCommentByIdAndPostId(Long id,Long postId);
    Optional<Comment> getSingleCommentByIdAndPostIdAndUserId(Long id,Long postId,Long userId);

    void deleteCommentByIdAndPostIdandUserId(Long id,Long postId,Long userId);

    void deleteMySingleCommentOnThisPost(Long id,Long postId,Long userId);

    void deleteCommentById(Long id);

    void deleteAllMyComments(Long userId);

    void deleteAllMyCommentsOnThisPost(Long userId,Long postId);


//    Page<Comment> search(String text, Long postId, Long userId, Long commentId, List<String> fields, Pageable pageable);

    void partiallyDeleteCommentById(Comment comment);
}
