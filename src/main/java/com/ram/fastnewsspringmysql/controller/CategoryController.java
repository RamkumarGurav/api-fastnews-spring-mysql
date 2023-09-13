package com.ram.fastnewsspringmysql.controller;

import com.ram.fastnewsspringmysql.collection.Category;
import com.ram.fastnewsspringmysql.collection.Category;
import com.ram.fastnewsspringmysql.collection.Tag;
import com.ram.fastnewsspringmysql.dto.MsgRBody;
import com.ram.fastnewsspringmysql.dto.RBody;
import com.ram.fastnewsspringmysql.error.CustomException;
import com.ram.fastnewsspringmysql.repository.CategoryRepository;
import com.ram.fastnewsspringmysql.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;


    @PostMapping("/admin-protected/categories")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody Category category){

        Optional<Category> foundCategoryOp=categoryRepository.findByCategoryName(category.getCategoryName());
        if(foundCategoryOp.isPresent()){
            throw new CustomException("This Category Already Exists", HttpStatus.BAD_REQUEST);
        }

        category.setCategoryName(category.getCategoryName().toUpperCase().trim());

        Category newCategory = categoryService.createCategory(category);


        RBody rbody = new RBody("success", newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(rbody);
    }


    @GetMapping("/admin-protected/categories/{id}")
    public ResponseEntity<Object> getSingleCategory(@PathVariable("id") Long id){

        Optional<Category> foundCategoryOp = categoryService.getSingleCategory(id);

        if (!foundCategoryOp.isPresent()){
            throw new CustomException("Category not found",HttpStatus.NOT_FOUND);
        }


        RBody rbody = new RBody("success", foundCategoryOp.get());
        return ResponseEntity.status(HttpStatus.OK).body(rbody);
    }




    @PatchMapping("/admin-protected/categories/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable("id") Long id,@Valid @RequestBody Category category){

        //CHECKING IF THE CATEGORY IS AVAILABLE
        Optional<Category> foundCategoryOp1=categoryService.getSingleCategory(id);
        if(!foundCategoryOp1.isPresent()){
            throw new CustomException("Tag Not Found",HttpStatus.NOT_FOUND);
        }

        Category foundCategory=foundCategoryOp1.get();

        //CHECKING IF THERE IS ANOTHER CATEGORY WITH THE SAME GIVE NAME
        Optional<Category> foundCategoryOp2=categoryRepository.findByCategoryName(category.getCategoryName());
        if(foundCategoryOp2.isPresent()){
            throw new CustomException("This Category Already Exists", HttpStatus.BAD_REQUEST);
        }


        //UPDATING CATEGORY

        foundCategory.setCategoryName(category.getCategoryName().toUpperCase().trim());

        Category updateCategory = categoryService.updateCategory(foundCategory);


        RBody rbody = new RBody("success", updateCategory);
        return ResponseEntity.status(HttpStatus.OK).body(rbody);
    }

    @DeleteMapping("/admin-protected/categories/{id}")
    public ResponseEntity<Object> permanentlyDeleteCategory(@PathVariable("id") Long id){

        Optional<Category>  foundCategoryOp=categoryService.getSingleCategory(id);
        if (!foundCategoryOp.isPresent()){
            throw new CustomException("Category not found",HttpStatus.NOT_FOUND);
        }


        categoryService.permanentlyDeleteCategory(id);


        MsgRBody rbody = new MsgRBody("success", "successfully deleted category with categoryName: "+foundCategoryOp.get().getCategoryName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }



    @GetMapping("/admin-protected/categories")
    public ResponseEntity<Object> getAllCategories(@RequestParam(required = false) String keyword,
                                              @RequestParam(defaultValue = "true") boolean active,
                                              @RequestParam(defaultValue = "false") boolean deleted,
                                              @RequestParam(required = false) Long categoryId,
                                              @RequestParam(defaultValue = "-createdAt") List<String> sort,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size) {


//--------------for single sort field-----------------
//            Sort sortByProperty=sort.startsWith("-")?Sort.by(Sort.Direction.DESC,sort.substring(1)):Sort.by(Sort.Direction.ASC,sort);

//--------------for MULTIPLE sort fieldS-----------------
        // MAKING SORT LIST
        List<Sort.Order> ordersA = new ArrayList<>();
        //FILTERING BASED ON "-" SYMBOL
        for (String field : sort) {
            if (field.startsWith("-")) {
                ordersA.add(new Sort.Order(Sort.Direction.DESC, field.substring(1)));
            } else {
                ordersA.add(new Sort.Order(Sort.Direction.ASC, field));
            }
        }

//        //----------------using stream and map-----------------------
//            List<Sort.Order> ordersB= sort.stream()
//                    .map(field -> {
//                        Sort.Direction direction = field.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
//                        String fieldName = field.startsWith("-") ? field.substring(1) : field;
//                        return new Sort.Order(direction, fieldName);
//                    })
//                    .collect(Collectors.toList());

        //CREATING SORT OBJECT WHICH TAKES STREAMABLE SORT.ORDER OBJECTS
        Sort sortProps = Sort.by(ordersA);

        //PAGEABLE
        Pageable pageable = PageRequest.of(page, size, sortProps);

        //GETTING POSTS PAGE
        Page<Category> categorysPage = categoryService.search(keyword,categoryId,active,deleted,pageable);

        RBody rbody = new RBody("success", categorysPage);
        return ResponseEntity.status(HttpStatus.OK).body(rbody);
    }



    @PatchMapping("/admin-protected/categories/deactivate/{id}/deactivate")
    public ResponseEntity<Object> deactivateCategory(@PathVariable("id") Long id){

        Optional<Category>  foundCategoryOp=categoryService.getSingleCategory(id);
        if (!foundCategoryOp.isPresent()){
            throw new CustomException("Category not found",HttpStatus.NOT_FOUND);
        }



        Category updatedCategory= categoryService.deactivateCategory(foundCategoryOp.get());

        MsgRBody rbody = new MsgRBody("success", "successfully deactivated category with categoryName: "+foundCategoryOp.get().getCategoryName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }

    @PatchMapping("/admin-protected/categories/{id}/activate")
    public ResponseEntity<Object> activateCategory(@PathVariable("id") Long id){

        Optional<Category>  foundCategoryOp=categoryService.getSingleCategory(id);
        if (!foundCategoryOp.isPresent()){
            throw new CustomException("Category not found",HttpStatus.NOT_FOUND);
        }



        Category updatedCategory= categoryService.activateCategory(foundCategoryOp.get());

        MsgRBody rbody = new MsgRBody("success", "successfully activated category with categoryName: "+foundCategoryOp.get().getCategoryName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }


    @PatchMapping("/admin-protected/categories/{id}/partial-delete")
    public ResponseEntity<Object> partiallyDelete(@PathVariable("id") Long id){

        Optional<Category>  foundCategoryOp=categoryService.getSingleCategory(id);
        if (!foundCategoryOp.isPresent()){
            throw new CustomException("Category not found",HttpStatus.NOT_FOUND);
        }



        Category updatedCategory= categoryService.partiallyDeleteCategory(foundCategoryOp.get());

        MsgRBody rbody = new MsgRBody("success", "successfully partially deleted category with categoryName: "+foundCategoryOp.get().getCategoryName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }



}
