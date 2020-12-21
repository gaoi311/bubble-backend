package com.buuble.blog.service;

import com.buuble.blog.entity.Tag;

import java.util.List;
import java.util.Map;

public interface OtherService {
    //获取推荐作者列表(根据作者所有博客被收藏总量）
    Map<String,Object> getHotAuthor();

    //获取最常用的15个标签
    Map<String,Object> getHotTags();

    //搜索关键字
    Map<String,Object> search(String keyword);
}
