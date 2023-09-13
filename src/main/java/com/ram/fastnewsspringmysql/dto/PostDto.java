package com.ram.fastnewsspringmysql.dto;

import com.ram.fastnewsspringmysql.collection.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String subtitle;
    private String description;
    private ArrayList<TagDto> tags;
    private CategoryDto category;
    private ArrayList<String> images;
    private int numberOfLikes;
    private Date publishedAt;
    private UserCommentDto author;

}
