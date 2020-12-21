package com.buuble.blog.repository;

import com.buuble.blog.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    Integer countByBlogId(Integer blogId);

    //  Collection save(Collection collection);
    @Transactional
    void deleteByBlogIdAndUserId(Integer blogId, Integer userId);

    Collection findByBlogIdAndUserId(Integer blogId, Integer userId);

    @Query(value = "select blogId from Collection where userId=?1")
    List<Integer> findBlogIdByUserId(Integer userId);

    List<Collection> findAllByBlogId(Integer blogId);

    List<Collection> findAllByBlogIdAndAndIsSaw(Integer blogId, String isSaw);

    Collection save(Collection collection);
}
