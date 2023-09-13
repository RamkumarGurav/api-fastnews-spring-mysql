package com.ram.fastnewsspringmysql.service;

import com.ram.fastnewsspringmysql.collection.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);

    Optional<Category> getSingleCategory(Long id);

    Optional<Category> getSingleCategoryByCategoryName(String categoryName);

    List<Category> getAllCategorys();

    void permanentlyDeleteCategory(Long id);

    Category updateCategory(Category category);

    Category deactivateCategory(Category category);

    Category activateCategory(Category category);

    Category partiallyDeleteCategory(Category category);

    Page<Category> search(String keyword, Long categoryId, boolean active, boolean deleted, Pageable pageable);
}
