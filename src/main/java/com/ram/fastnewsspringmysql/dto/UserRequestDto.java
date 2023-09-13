package com.ram.fastnewsspringmysql.dto;

import com.ram.fastnewsspringmysql.collection.Gender;
import com.ram.fastnewsspringmysql.collection.User;
import lombok.Data;

@Data
public class UserRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private String avatar;
    private String mobile;
    private String password;
    private String confirmPassword;
    private String occupation;
}
