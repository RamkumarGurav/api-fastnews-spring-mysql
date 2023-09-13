package com.ram.fastnewsspringmysql.service;

import com.ram.fastnewsspringmysql.collection.Tag;
import com.ram.fastnewsspringmysql.error.CustomException;
import com.ram.fastnewsspringmysql.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService{
    
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag createTag(Tag tag) {

        Optional<Tag> tagOptional = tagRepository.findByTagName(tag.getTagName());

        if(tagOptional.isPresent()){
            throw new CustomException("This Tag has already Created", HttpStatus.BAD_REQUEST);
        }

        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Tag tag) {
        Optional<Tag> tagOptional = tagRepository.findByTagName(tag.getTagName());

        if(tagOptional.isPresent()){
            throw new CustomException("This Tag has already Created", HttpStatus.BAD_REQUEST);
        }

        tag.setUpdated(true);

        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> getActiveSingleTag(Long id, boolean active) {
        return tagRepository.findByTagIdAndActive(id,active);
    }

    @Override
    public Optional<Tag> getSingleTag(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public Optional<Tag> getSingleTagByName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }

    @Override
    public void permanentlyDeleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag deactivateTag(Tag tag) {
        tag.setUpdated(true);
        tag.setActive(false);
        tag.setDeleted(false);
        return tagRepository.save(tag);
    }

    @Override
    public Tag activateTag(Tag tag) {
        tag.setUpdated(true);
        tag.setActive(true);
        tag.setDeleted(false);
        return tagRepository.save(tag);
    }

    @Override
    public Tag partiallyDeleteTag(Tag tag) {
        tag.setUpdated(true);
        tag.setActive(false);
        tag.setDeleted(true);
        return tagRepository.save(tag);
    }

    @Override
    public Page<Tag> search(String keyword, Long tagId, boolean active, boolean deleted, Pageable pageable) {
        return tagRepository.searchByKeywordAndTagIdAndActiveAndDeleted(keyword,tagId,active,deleted,pageable);
    }

  
}
