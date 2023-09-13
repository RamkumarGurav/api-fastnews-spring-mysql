package com.ram.fastnewsspringmysql.repository;

import com.ram.fastnewsspringmysql.collection.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {



    Page<Comment> findAllByPostId(Long postId,Pageable pageable);

    Optional<Comment> findAByCommentIdAndPostId(Long commentId,Long postId);
//    Optional<Comment> findByCommentIdAndPostIdAndUser_UserId(Long commentId,Long postId,Long userUserId);


    @Query("SELECT c FROM Comment c WHERE " +
            "(:postId IS NULL OR c.postId=:postId) " +
            "AND (:active=:active) " +
            "AND (:deleted=:deleted) " +
            "AND (:userId IS NULL OR EXISTS (SELECT 1 FROM c.user u WHERE u.userId=:userId )) " +
            "AND (:commentId IS NULL OR c.commentId=:commentId ) " +
            "AND (:keyword IS NULL OR " +
                                "(" +
                                    "UPPER(c.text) LIKE CONCAT('%', UPPER(:keyword), '%') " +
                                 ")"
                +")")
    Page<Comment> searchCommentsByKeywordAndPostIdAndUserIdAndCommentId(String keyword,boolean active,boolean deleted, Long postId, Long userId, Long commentId, Pageable pageable);




    List<Comment> findAllByUser_UserId(Long userId);
    Optional<Comment> findByPostIdAndUser_UserId(Long postId, Long userId);


    Optional<Comment> findByCommentIdAndActive(Long commentId, boolean active);

    Optional<Comment> findByCommentIdAndPostIdAndUser_UserId(Long commentId, Long postId, Long userId);

    Optional<Comment> findByCommentIdAndPostIdAndUser_UserIdAndActive(Long commentId, Long postId, Long userId, boolean active);

    void deleteByCommentIdAndPostIdAndUser_UserId(Long commentId, Long postId, Long userId);

    void deleteAllByUser_UserId(Long userId);

    void deleteAllByPostIdAndUser_UserId(Long postId, Long userId);

    Optional<Comment> findByCommentIdAndUser_UserId(Long commentId, Long userId);

    Optional<Comment> findByCommentIdAndPostId(Long commentId, Long postId);

    void deleteAllByPostId(Long id);
}
