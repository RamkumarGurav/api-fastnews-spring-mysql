package com.ram.fastnewsspringmysql.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Long commentId;
    private String text;
    private UserCommentDto user;
    private String postId;


}
