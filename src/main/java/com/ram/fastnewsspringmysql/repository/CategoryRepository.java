package com.ram.fastnewsspringmysql.repository;

import com.ram.fastnewsspringmysql.collection.Category;
import com.ram.fastnewsspringmysql.collection.Role;
import com.ram.fastnewsspringmysql.collection.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByCategoryName(String string);
    Optional<Tag> findByCategoryIdAndActive(Long categoryId , boolean active);

    @Query("SELECT c FROM Category c WHERE " +
            "(:categoryId IS NULL OR c.categoryId=:categoryId) " +
            "AND (c.active=:active) " +
            "AND ( c.deleted=:deleted) " +
            "AND (:keyword IS NULL OR UPPER(c.categoryName) LIKE CONCAT('%', UPPER(:keyword), '%') )")
    Page<Category> searchByKeywordAndCategoryIdAndActiveAndDeleted(String keyword, Long categoryId, boolean active, boolean deleted, Pageable pageable);


}
