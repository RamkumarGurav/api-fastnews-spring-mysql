package com.ram.fastnewsspringmysql.dto;

import com.ram.fastnewsspringmysql.collection.Gender;
import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private String avatar;
    private String mobile;
    private String occupation;
}
