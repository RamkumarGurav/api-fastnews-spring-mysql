package com.ram.fastnewsspringmysql.repository;

import com.ram.fastnewsspringmysql.collection.Category;
import com.ram.fastnewsspringmysql.collection.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    Optional<Tag> findByTagName(String tagName);

    Optional<Tag> findByTagIdAndActive(Long tagId, boolean active);

    @Query("SELECT t FROM Tag t WHERE " +
            "(:tagId IS NULL OR t.tagId=:tagId) " +
            "AND (t.active=:active) " +
            "AND (t.deleted=:deleted) " +
            "AND (:keyword IS NULL OR UPPER(t.tagName) LIKE CONCAT('%', UPPER(:keyword), '%') )")
    Page<Tag> searchByKeywordAndTagIdAndActiveAndDeleted(String keyword, Long tagId, boolean active, boolean deleted, Pageable pageable);


}
