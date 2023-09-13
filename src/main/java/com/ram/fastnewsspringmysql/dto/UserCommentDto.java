package com.ram.fastnewsspringmysql.dto;

import lombok.Data;

@Data
public class UserCommentDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
}
