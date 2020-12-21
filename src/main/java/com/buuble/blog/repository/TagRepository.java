package com.buuble.blog.repository;

import com.buuble.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query(value = "select tagName from Tag where tagId in(?1) ")
    List<String> findTagNameByTagIdIn(List<Integer> tagId);

    Tag findByTagId(Integer tagId);
}
