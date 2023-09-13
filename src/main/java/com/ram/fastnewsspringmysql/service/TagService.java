package com.ram.fastnewsspringmysql.service;

import com.ram.fastnewsspringmysql.collection.Tag;
import com.ram.fastnewsspringmysql.collection.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Tag createTag(Tag tag);

    Tag updateTag(Tag tag);

    List<Tag> getAllTags();

    Optional<Tag> getActiveSingleTag(Long id, boolean active);

    Optional<Tag> getSingleTag(Long id);

    Optional<Tag> getSingleTagByName(String tagName);

    void permanentlyDeleteTag(Long id);

    Tag deactivateTag(Tag tag);

    Tag activateTag(Tag tag);

    Tag partiallyDeleteTag(Tag tag);

    Page<Tag> search(String keyword, Long tagId, boolean active, boolean deleted, Pageable pageable);
}
