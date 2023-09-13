package com.ram.fastnewsspringmysql.collection;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@MappedSuperclass
//to make sure that these fields also included in the other tables (entities those uses this annotation)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {



    private boolean deleted = false;

    private boolean updated = false;

    private  boolean active = true;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    // we made updatable as false bcz we want this field inserted into
// table at starting(during initial creating of a record) only after that we dont want to update this field


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    // we made updatable as false bcz we want this field inserted into
// table at starting(during initial creating of a record) only after that we dont want to update this field

    @LastModifiedBy
    @Column(insertable = false)
    private String lastModifiedBy;
    // we made insertable as false bcz we dont to insert this field a value initially while
    // creating a record but whenever someone updates a record then only it must insert a new value or change the value


    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedAt;
    // we made insertable as false bcz we dont to insert this field a value initially while
    // creating a record but whenever someone updates a record then only it must insert a new value or change the value



}
