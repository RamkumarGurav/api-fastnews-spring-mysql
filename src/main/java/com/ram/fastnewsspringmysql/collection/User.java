package com.ram.fastnewsspringmysql.collection;


import com.fasterxml.jackson.annotation.JsonInclude;


import com.ram.fastnewsspringmysql.annotations.EnumChecker;
import com.ram.fastnewsspringmysql.annotations.FieldsValueMatch;
import com.ram.fastnewsspringmysql.annotations.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field ="password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match"
        )
//        @FieldsValueMatch(
//                field = "email",
//                fieldMatch = "confirmEmail",
//                message = "Emails do not match"
//
//        )

})
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(
                name = "email_unique",
                columnNames = "email"
        )
)
public class User extends BaseEntity implements UserDetails  {


    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    private Long userId;


    @NotBlank(message = "Please Provide FirstName")
    @Size(min = 2,message = "FirstName must contain at least 2 characters")
    private String firstName;

    @NotBlank(message = "Please Provide LastName")
    @Size(min = 2,message = "LastName must contain at least 2 characters")
    private String lastName;


    @NotBlank(message = "Please Provide email")
    @Email(message = "Please enter valid email")
    private String email;


    @NotBlank(message="Gender must not be blank")
    @EnumChecker(message = "Provide Valid Gender",enumClass = Gender.class)
    private String gender;


    @NotBlank(message="Confirm Password must not be blank")
    @Size(min=5, message="Password must be at least 5 characters long")
    @PasswordValidator
    private String password;

    @NotBlank(message="Confirm Password must not be blank")
    @Size(min=5, message="Confirm Password must be at least 5 characters long")
    @Transient //this field will be not pushed to the sql users table
    private String confirmPassword;



    private Date passwordChangedAt;
    private String passwordResetToken;
    private Date passwordResetTokenExpires;



    @Size(min=2, message="avatar must be at least 2 characters long")
    private String avatar ="default.png";

    @NotBlank(message = "Please Provide mobile number")
    @Pattern(regexp = "(^[0-9]{10}$)",message = "Please Enter valid mobile number")
    private String mobile;

    @Size(min=2, message="job name must be at least 2 characters long")
    private String occupation;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",referencedColumnName = "roleId",nullable = false)
    private Role role;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRoleName()));

    }

    @Override
    public String getPassword() {

        return password;
    }





    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}




//-------USE OF JsonInclude(JsonInclude.Include.NON_NULL)
//The @JsonInclude(JsonInclude.Include.NON_NULL) annotation is used in Java with Jackson, a popular JSON library,
// to configure how null values should be handled during JSON serialization (i.e., converting Java objects to
// JSON format).
//
//In this case, when you annotate a class or a property with @JsonInclude(JsonInclude.Include.NON_NULL), it
// tells Jackson to exclude any properties that have null values from the JSON output. In other words, only
// non-null properties will be included in the generated JSON.