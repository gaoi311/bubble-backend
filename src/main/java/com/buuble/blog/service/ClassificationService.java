package com.buuble.blog.service;

import com.buuble.blog.entity.Classification;

import java.util.List;
import java.util.Map;

public interface ClassificationService {

    //根据userId查询此用户已创建的分类列表
    Map<String, Object> classificationList(Integer userId);

    //用户创建一个新的分类
    Classification addClassification(Integer userId, String classificationName);

    //用户删除一个分类（并且会删除分类下的所有博客）
    boolean deleteClassification(Integer classificationId);

}
