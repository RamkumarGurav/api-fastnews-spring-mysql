package com.ram.fastnewsspringmysql.controller;

import com.ram.fastnewsspringmysql.collection.Tag;
import com.ram.fastnewsspringmysql.collection.Tag;
import com.ram.fastnewsspringmysql.collection.Tag;
import com.ram.fastnewsspringmysql.dto.MsgRBody;
import com.ram.fastnewsspringmysql.dto.RBody;
import com.ram.fastnewsspringmysql.error.CustomException;
import com.ram.fastnewsspringmysql.repository.TagRepository;
import com.ram.fastnewsspringmysql.service.TagService;
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
public class TagController {
    @Autowired
    private TagService tagService;
    
    @Autowired
    private TagRepository tagRepository;


    @PostMapping("/admin-protected/tags")
    public ResponseEntity<Object> createTag(@Valid @RequestBody Tag tag){

        Optional<Tag> foundTagOp=tagRepository.findByTagName(tag.getTagName());
        if(foundTagOp.isPresent()){
            throw new CustomException("This Tag is Already Exists", HttpStatus.BAD_REQUEST);
        }

        tag.setTagName(tag.getTagName().toUpperCase().trim());

        Tag newTag = tagService.createTag(tag);


        RBody rbody = new RBody("success", newTag);
        return ResponseEntity.status(HttpStatus.CREATED).body(rbody);
    }


    @GetMapping("/admin-protected/tags/{id}")
    public ResponseEntity<Object> getSingleTag(@PathVariable("id") Long id){

        Optional<Tag> foundTagOp = tagService.getSingleTag(id);

        if (!foundTagOp.isPresent()){
            throw new CustomException("Tag not found",HttpStatus.NOT_FOUND);
        }


        RBody rbody = new RBody("success", foundTagOp.get());
        return ResponseEntity.status(HttpStatus.OK).body(rbody);
    }






    @PatchMapping("/admin-protected/tags/{id}")
    public ResponseEntity<Object> updateTag(@PathVariable("id") Long id,@Valid @RequestBody Tag tag){

        //CHECKING IF THE TAG IS AVAILABLE
        Optional<Tag> foundTagOp1=tagService.getSingleTag(id);
        if(!foundTagOp1.isPresent()){
            throw new CustomException("Tag Not Found",HttpStatus.NOT_FOUND);
        }

        Tag foundTag=foundTagOp1.get();



        //checking if the given tagname has a tag IN DB
        Optional<Tag> foundTagOp2=tagRepository.findByTagName(tag.getTagName());
        if(foundTagOp2.isPresent()){
            throw new CustomException("This Tag Already Exists", HttpStatus.BAD_REQUEST);
        }


        //updating tag
        foundTag.setTagName(tag.getTagName().toUpperCase().trim());

        Tag updatedTag = tagService.updateTag(foundTag);


        RBody rbody = new RBody("success", updatedTag);
        return ResponseEntity.status(HttpStatus.OK).body(rbody);
    }


    @DeleteMapping("/admin-protected/tags/{id}")
    public ResponseEntity<Object> permanentlyDeleteTag(@PathVariable("id") Long id){

        Optional<Tag>  foundTagOp=tagService.getSingleTag(id);
        if (!foundTagOp.isPresent()){
            throw new CustomException("Tag not found",HttpStatus.NOT_FOUND);
        }


        tagService.permanentlyDeleteTag(id);


        MsgRBody rbody = new MsgRBody("success", "successfully deleted tag with tagName: "+foundTagOp.get().getTagName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }


    @GetMapping("/admin-protected/tags")
    public ResponseEntity<Object> getAllTags(@RequestParam(required = false) String keyword,
                                                   @RequestParam(defaultValue = "true") boolean active,
                                                   @RequestParam(defaultValue = "false") boolean deleted,
                                                   @RequestParam(required = false) Long tagId,
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
        Page<Tag> tagsPage = tagService.search(keyword,tagId,active,deleted,pageable);

        RBody rbody = new RBody("success", tagsPage);
        return ResponseEntity.status(HttpStatus.OK).body(rbody);
    }



    @PatchMapping("/admin-protected/tags/deactivate/{id}/deactivate")
    public ResponseEntity<Object> deactivateTag(@PathVariable("id") Long id){

        Optional<Tag>  foundTagOp=tagService.getSingleTag(id);
        if (!foundTagOp.isPresent()){
            throw new CustomException("Tag not found",HttpStatus.NOT_FOUND);
        }



        Tag updatedTag= tagService.deactivateTag(foundTagOp.get());

        MsgRBody rbody = new MsgRBody("success", "successfully deactivated tag with tagName: "+foundTagOp.get().getTagName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }

    @PatchMapping("/admin-protected/tags/{id}/activate")
    public ResponseEntity<Object> activateTag(@PathVariable("id") Long id){

        Optional<Tag>  foundTagOp=tagService.getSingleTag(id);
        if (!foundTagOp.isPresent()){
            throw new CustomException("Tag not found",HttpStatus.NOT_FOUND);
        }



        Tag updatedTag= tagService.activateTag(foundTagOp.get());

        MsgRBody rbody = new MsgRBody("success", "successfully activated tag with tagName: "+foundTagOp.get().getTagName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }


    @PatchMapping("/admin-protected/tags/{id}/partial-delete")
    public ResponseEntity<Object> partiallyDelete(@PathVariable("id") Long id){

        Optional<Tag>  foundTagOp=tagService.getSingleTag(id);
        if (!foundTagOp.isPresent()){
            throw new CustomException("Tag not found",HttpStatus.NOT_FOUND);
        }



        Tag updatedTag= tagService.partiallyDeleteTag(foundTagOp.get());

        MsgRBody rbody = new MsgRBody("success", "successfully partially deleted tag with tagName: "+foundTagOp.get().getTagName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }



}
