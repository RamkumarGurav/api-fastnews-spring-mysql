package com.ram.fastnewsspringmysql.dto;

import com.ram.fastnewsspringmysql.annotations.EnumChecker;
import com.ram.fastnewsspringmysql.annotations.PasswordValidator;
import com.ram.fastnewsspringmysql.collection.Gender;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data
public class UpdateMeRequest {

    @Size(min = 2,message = "firstName must contain atleast 2 characters")
    private String firstName;

    @Size(min = 2,message = "firstName must contain atleast 2 characters")
    private String lastName;


    @Email(message = "Please enter valid email")
    @Size(min=2, message="Please provide valid email")
    private String email;


    @EnumChecker(message = "Provide Valid Gender",enumClass = Gender.class)
    private String gender;

    @Size(min=2, message="avatar must be at least 2 characters long")
    private String avatar;

    @Pattern(regexp = "(^[0-9]{10}$)",message = "Please Enter valid mobile number")
    private String mobile;


    @Size(min=2, message="Occupation name must be at least 2 characters long")
    private String occupation;

}
