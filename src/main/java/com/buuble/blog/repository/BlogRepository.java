package com.buuble.blog.repository;


import com.buuble.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Integer> {

    //根据userId查询此用户所有博客，并根据ReleaseTime倒序排列
    List<Blog> findByUserIdOrderByReleaseTimeDesc(Integer userId);

    //根据blogId查询博客全部信息
    Blog findByBlogId(Integer blogId);

    //保存博客，blogId存在时为update，不存在时为insert
//    Blog save(Blog blog);

    //根据userId和isReleased查询此用户已发布或未发布博客，并根据ReleaseTime倒序排列
    List<Blog> findByUserIdAndIsReleasedOrderByReleaseTimeDesc(Integer userId, String str);

    //根据blogId删除博客
    @Transactional
    void deleteByBlogId(Integer blogId);

    //根据classificationId查此分类下博客
    List<Blog> findByClassificationId(Integer ClassificationId);

    List<Blog> findAllByUserIdAndIsReleased(Integer userId, String isreleased);

    List<Blog> findByUserId(Integer userId);
    //从title和BlogContent中查询
    List<Blog> findByTitleLikeOrBlogContentLike(String keyword,String keyword1);
}
