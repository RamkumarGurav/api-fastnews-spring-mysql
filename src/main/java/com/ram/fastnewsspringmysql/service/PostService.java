package com.ram.fastnewsspringmysql.service;

import com.ram.fastnewsspringmysql.collection.Post;
import com.ram.fastnewsspringmysql.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(Post post);
    Post udpatePost(Post post);


    List<Post> findAll();


    Page<Post> getAllPostsWithPage(Pageable pageable);



    Optional<Post> getSinglePost(Long id);

    void permanentlyDeletePost(Long id);

    Post partiallyDeletePost(Post post);

    Post undeletePostButNotActive(Post post);
    Post deactivatePost(Post post);
    Post activatePost(Post post);

    Optional<Post> getPostByIdAndActiveAndDeleted(Long id, boolean active,boolean deleted);



    PostDto postToPostDto(Post post);


//    Page<Post> search(Long authorId,String title, String subtitle, String categoryName, Long tagId, List<String> fields, Pageable pageable);
//
//    Page<Post> searchByAdmin(Long authorId, String title, String subtitle, String categoryName, Long tagId, List<String> fields, Pageable pageable);

    Page<Post> searchPostsByKeywordAndCategoryAndTagAndAuthorId(String keyword, boolean active, boolean deleted, Long authorId, String categoryName, String tagName, Pageable pageable);

    Optional<Post> getSinglePostByIdAndAuthorId(Long id, Long authorId);

    void permanentlyDeletePostById(Long id);

//    void permanentlyDeletePostByIdAndAuthorId(Long id, Long authorId);
//
//    void permanentlyDeleteAllByAuthorId(Long authorId);
}
