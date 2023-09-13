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
@Table(name = "tags",
uniqueConstraints = @UniqueConstraint(
        name = "tagName_constraint",
        columnNames = "tagName"
)
)
public class Tag extends BaseEntity{


    @Id
    @SequenceGenerator(
            name = "tag_sequence",
            sequenceName = "tag_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tag_sequence")
    private Long tagId;

    @NotBlank(message = "Please Provide Tag Name")
    @Size(min = 2,max = 50,message = "Tag Name must be between 2 and 50 characters in length")
    private String tagName;

}
