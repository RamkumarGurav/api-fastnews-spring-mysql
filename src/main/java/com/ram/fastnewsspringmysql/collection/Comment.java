package com.ram.fastnewsspringmysql.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {


    @Id
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence")
    private Long commentId;
    
    @NotBlank(message = "Please provide content of the review")
    @Size(min = 3,max = 5000,message = "Content of the review must be between 3 and 5000 characters")
    private String text;

    //we are not using any cascade type bcz we user field as read only
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "userId",nullable = false,insertable = false,updatable = false)
    private User user;

    private Long postId;




}
