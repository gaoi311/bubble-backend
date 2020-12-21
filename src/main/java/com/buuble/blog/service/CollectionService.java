package com.buuble.blog.service;

import com.buuble.blog.entity.Blog;
import com.buuble.blog.entity.Collection;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface CollectionService {
    Collection addCollection(Integer userId, Integer blogId);

    @Transactional
    boolean deleteCollection(Integer userId, Integer blogId);

    Map<String, Object> getMyCollection(Integer userId);
}
