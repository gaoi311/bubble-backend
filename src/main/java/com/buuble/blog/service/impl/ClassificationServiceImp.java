package com.buuble.blog.service.impl;

import com.buuble.blog.entity.Classification;
import com.buuble.blog.repository.ClassificationRepository;
import com.buuble.blog.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassificationServiceImp implements ClassificationService {

    @Autowired
    public ClassificationRepository classificationRepository;

    public Classification addClassification(Integer userId, String classificationName) {
        Classification classification = new Classification();
        classification.setUserId(userId);
        classification.setClassificationName(classificationName);
        return classificationRepository.save(classification);
    }

    public Map<String, Object> classificationList(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();
        List<Classification> classifications = classificationRepository.findByUserId(userId);
        for (Classification classification : classifications) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("classificationId", classification.getClassificationId());
            map1.put("classificationName", classification.getClassificationName());
            data.add(map1);
        }

        map.put("data", data);
        return map;
    }

    public boolean deleteClassification(Integer classificationId) {
        classificationRepository.deleteByClassificationId(classificationId);
        return classificationRepository.findByClassificationId(classificationId) == null;

    }
}
