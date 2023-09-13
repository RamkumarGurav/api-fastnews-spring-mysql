package com.ram.fastnewsspringmysql.dto;

import com.ram.fastnewsspringmysql.collection.Category;
import com.ram.fastnewsspringmysql.collection.Comment;
import com.ram.fastnewsspringmysql.collection.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostRequest {
    @NotBlank(message = "Please provide product name")
    @Size(min = 3,max = 200,message = "Post name must be between 3 and 200 characters in length")
    private String title;

    @NotBlank(message = "Please provide product name")
    @Size(min = 3,max = 200,message = "Post name must be between 3 and 200 characters in length")
    private String subtitle;

    @NotBlank(message = "Please provide product description")
    @Size(min = 10,message = "Post name must be between 10 and 5000 characters in length")
    private String description;

    @NotNull(message = "Please provide Tag names")
    @Size(min = 1, max = 10, message = "Tag names List size must be between 1 and 10")
    private ArrayList<String> tagNames;

    @NotBlank(message = "Please provide Category name")
    private String categoryName;


    @Size(min = 1, max = 10, message = "Images List size must be between 1 and 10")
    private ArrayList<String> images;

}
