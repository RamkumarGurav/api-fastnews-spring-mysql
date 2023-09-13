package com.ram.fastnewsspringmysql.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthorRequest {
    @NotBlank(message = "Provide occupation name")
    @Size(min=2, message="occupation name must be at least 2 characters long")
    private String occupation;


}
