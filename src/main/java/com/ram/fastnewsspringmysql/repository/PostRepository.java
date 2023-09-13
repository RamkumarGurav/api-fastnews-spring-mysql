package com.ram.fastnewsspringmysql.repository;

import com.ram.fastnewsspringmysql.collection.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    Optional<Post> findByPostIdAndAuthor_UserId(Long postId, Long userId);


    List<Post> findAllByAuthor_UserId(Long userId);

    //pagination
    Page<Post> findAll(Pageable pageable);


    @Query("SELECT p FROM Post p WHERE " +
            "(:categoryName IS NULL OR EXISTS (SELECT 1 FROM p.category c WHERE UPPER(c.categoryName) = UPPER(:categoryName) )) " +
            "AND (:active=:active) " +
            "AND (:deleted=:deleted) " +
            "AND (:tagName IS NULL OR EXISTS (SELECT 1 FROM p.tags t WHERE UPPER(t.tagName) = UPPER(:tagName))) " +
            "AND (:authorId IS NULL OR EXISTS (SELECT 1 FROM p.author a WHERE a.userId=:authorId) ) " +
            "AND (:keyword IS NULL OR " +
                     "(" +
                            "UPPER(p.title) LIKE CONCAT('%', UPPER(:keyword), '%') " +
                            "OR UPPER(p.subtitle) LIKE CONCAT('%', UPPER(:keyword), '%') " +
                            "OR UPPER(p.description) LIKE CONCAT('%', UPPER(:keyword), '%')" +
                    " )" +
                 ")"
            )
    Page<Post> searchPostsByKeywordAndCategoryAndTagAndAuthorId(String keyword,boolean active,boolean deleted,Long authorId, String categoryName, String tagName, Pageable pageable);




    Optional<Post> findByPostIdAndActive(Long id, boolean active);

    Optional<Post> findByPostIdAndActiveAndDeleted(Long postId, boolean active, boolean deleted);

    void deleteAllByAuthor_UserId(Long userId);

//    void DeleteByPostIdAndAuthor_UserId(Long postId, Long authorId);
//


}

