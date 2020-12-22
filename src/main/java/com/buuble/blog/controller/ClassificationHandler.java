package com.buuble.blog.controller;

import com.buuble.blog.entity.Classification;
import com.buuble.blog.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClassificationHandler {
    @Autowired
    private ClassificationService classificationService;

    @GetMapping("/classificationsnames/{userId}")
    public Map<String, Object> getClassification(@PathVariable("userId") Integer userId) {
        Map<String, Object> map = classificationService.classificationList(userId);
        Map<String, Object> status = new HashMap<>();
        if (map.get("data") != null) {
            status.put("code", 200);
            status.put("msg", "获取成功");
            map.put("status", status);
            return map;
        } else {
            status.put("code", 404);
            status.put("msg", "获取失败");
            Map<String, Object> map1 = new HashMap<>();
            map1.put("status", status);
            return map1;
        }
    }

    @PostMapping("/classification")
    public Map<String,Object> addClassification(@RequestParam("userId") Integer userId,
                                                @RequestParam("classificationName") String classificationName){
        Map<String,Object> map = new HashMap<>();

        Map<String,Object> status = new HashMap<>();
        Classification classification = classificationService.addClassification(userId, classificationName);
        if (classification.getClassificationId()!=null){
            Map<String,Object> data = new HashMap<>();
            data.put("classificationId",classification.getClassificationId());
            map.put("data",data);
            status.put("code",200);
            status.put("msg","添加成功");
        }else {
            status.put("code",404);
            status.put("msg","添加失败");
        }
        map.put("status",status);
        return map;
    }

    @DeleteMapping("/classification/{classificationId}")
    public Map<String, Object> deleteClassification(@PathVariable("classificationId") Integer classificationId) {
        boolean flag = classificationService.deleteClassification(classificationId);
        Map<String, Object> status = new HashMap<>();
        if (flag) {
            status.put("code", 200);
            status.put("msg", "删除成功");
        } else {
            status.put("code", 404);
            status.put("msg", "删除失败");
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("status", status);
        return map1;
    }
}
