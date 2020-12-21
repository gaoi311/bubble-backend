package com.buuble.blog.service;

import com.buuble.blog.entity.Blog;
import com.buuble.blog.entity.Blogtag;
import com.buuble.blog.entity.Picture;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

public interface BlogService {

    //保存博客，但未发布 （博客基本信息保存到blog表）
    Blog addBlog(Blog blog, List<Integer> tagIds);

    // 保存博客并发布 （同上）
    //   Blog releaseBlog(Blog blog);

    //用户删除自己的某一博客（picture表里的此博客图片和blogtag里的博客对应标签自动删除）
    boolean deleteBlog(Integer blogId);

    //展示某一博客
    Map<String, Object> showBlog(Integer blogId);


    //查看别人博客列表（只能查看已发布）
    Map<String, Object> showSomeoneBlogList(Integer userId);

    //获取分类及文章信息
    Map<String, Object> getClassificationAndBlog(Integer userId);

    //按时间倒序获取当前用户最近的博客列表
    Map<String, Object> getRecentBlogs(Integer userId);
}
