package com.ram.fastnewsspringmysql.service;

import com.ram.fastnewsspringmysql.collection.Category;
import com.ram.fastnewsspringmysql.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        category.setUpdated(true);
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getSingleCategory(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> getSingleCategoryByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }

    @Override
    public void permanentlyDeleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category deactivateCategory(Category category) {
        category.setUpdated(true);
        category.setActive(false);
        category.setDeleted(false);
        return categoryRepository.save(category);
    }

    @Override
    public Category activateCategory(Category category) {
        category.setUpdated(true);
        category.setActive(true);
        category.setDeleted(false);
        return categoryRepository.save(category);
    }

    @Override
    public Category partiallyDeleteCategory(Category category) {
        category.setUpdated(true);
        category.setActive(false);
        category.setDeleted(true);
        return categoryRepository.save(category);
    }

    @Override
    public Page<Category> search(String keyword, Long categoryId, boolean active, boolean deleted, Pageable pageable) {
        return categoryRepository.searchByKeywordAndCategoryIdAndActiveAndDeleted(keyword,categoryId,active,deleted,pageable);
    }

}
