package com.ram.fastnewsspringmysql.dto;

import com.ram.fastnewsspringmysql.collection.Gender;
import lombok.Data;

import java.util.List;

@Data
public class UserWithRoleNameDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private String avatar;
    private String mobile;
    private String occupation;
    private String roleName;

}
