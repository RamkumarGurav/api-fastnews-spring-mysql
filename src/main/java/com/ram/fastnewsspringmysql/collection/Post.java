package com.ram.fastnewsspringmysql.collection;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts"
)
public class Post extends BaseEntity {


    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence")
    private Long postId;

    @NotBlank(message = "Please provide product name")
    @Size(min = 3, max = 200, message = "Post name must be between 3 and 200 characters in length")
    private String title;

    @NotBlank(message = "Please provide product name")
    @Size(min = 3, max = 200, message = "Post name must be between 3 and 200 characters in length")
    private String subtitle;

    @NotBlank(message = "Please provide product description")
    @Size(min = 10, message = "Post name must be between 10 and 5000 characters in length")
    @Column(length = 10000) // Change the length as needed
    private String description;


    private List<Long> likes = new ArrayList<>(0);

    private int numberOfLikes = 0;


    //we are not using any Cascade type bcz we tags field as read only
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_tag",
           joinColumns = @JoinColumn(name = "post_id",referencedColumnName = "postId"),
            inverseJoinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "tagId")
    )
    private List<Tag> tags;


    //we are not using any cascade type bcz we category field as read only
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id",referencedColumnName = "categoryId",nullable = false)
    private Category category;


    @Size(min = 1, max = 10, message = "images list size must be between 1 and 10")
    private ArrayList<String> images = new ArrayList<>(0);

    //we are not using any cascade type bcz we author field as read only
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "userId",nullable = false)
    private User author;



    private Date publishedAt = new Date();




    public void updateNumOfLikes() {
        this.setNumberOfLikes(this.getLikes() == null ? 0 : this.getLikes().size());

    }


}
