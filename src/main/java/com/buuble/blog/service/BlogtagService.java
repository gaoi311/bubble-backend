package com.buuble.blog.service;

import com.buuble.blog.entity.Blogtag;
import com.buuble.blog.entity.Picture;

import java.util.List;
import java.util.Map;

public interface BlogtagService {

    //保存用户选定所有标签到blogtag表
    // List<Blogtag> addBlogtag(List<Blogtag> blogtags);

    //更新此博客所有标签
    //List<Blogtag> updateBlogtag(List<Blogtag> blogtags);

    Map<String, Object> getBlogsByTag(Integer tagId);

    List<Object> getTagsByBlogId(Integer blogId);
}
