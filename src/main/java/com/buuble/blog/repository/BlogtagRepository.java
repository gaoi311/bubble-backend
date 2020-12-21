package com.buuble.blog.repository;

import com.buuble.blog.entity.Blog;
import com.buuble.blog.entity.Blogtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


public interface BlogtagRepository extends JpaRepository<Blogtag, Integer> {

    @Query(value = "select tagId from Blogtag where blogId=?1")
    List<Integer> findTagIdByBlogId(Integer blogId);

    List<Blogtag> findByBlogId(Integer blogId);

    List<Blogtag> findByTagId(Integer tagId);

    Integer countByTagId(Integer tagId);

    // List<Blogtag> saveAll(List<Blogtag> blogtags);  //插入

    // @Transactional
    // void deleteInBatch(List<Blogtag> blogtag);  //batch删除
}
