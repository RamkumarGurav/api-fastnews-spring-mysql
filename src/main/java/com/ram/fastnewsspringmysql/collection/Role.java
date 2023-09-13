package com.ram.fastnewsspringmysql.collection;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles",
        uniqueConstraints = @UniqueConstraint(
                name = "roleName_constraint",
                columnNames = "roleName"
        )
)
public class Role extends BaseEntity{

    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_sequence")
    private Long roleId;

    @NotBlank(message = "Please Provide Role Name")
    @Size(min = 2,max = 50,message = "Role Name must be between 2 and 50 characters in length")
    private String roleName;


}

