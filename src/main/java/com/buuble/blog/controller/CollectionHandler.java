package com.buuble.blog.controller;

import com.buuble.blog.entity.Collection;
import com.buuble.blog.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CollectionHandler {
    @Autowired
    private CollectionService collectionService;

    @PostMapping("/collection")  //收藏博客
    public Map<String, Object> addCollection(@RequestParam("userId") Integer userId,
                                             @RequestParam("blogId") Integer blogId) {
        Collection collection = collectionService.addCollection(userId, blogId);
        Map<String, Object> status = new HashMap<>();
        if (collection != null) {
            status.put("code", 200);
            status.put("msg", "收藏成功");
        } else {
            status.put("code", 404);
            status.put("msg", "收藏失败");
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("status", status);
        return map1;
    }

    @DeleteMapping("/collection")  //删除收藏
    public Map<String, Object> deleteCollection(@RequestParam("userId") Integer userId,
                                                @RequestParam("blogId") Integer blogId) {
        boolean flag = collectionService.deleteCollection(userId, blogId);
        Map<String, Object> status = new HashMap<>();
        if (flag) {
            status.put("code", 200);
            status.put("msg", "取消成功");
        } else {
            status.put("code", 404);
            status.put("msg", "取消失败");
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("status", status);
        return map1;
    }

    @GetMapping("/collections/{userId}")
    public Map<String, Object> getMyCollection(@PathVariable("userId") Integer userId) {
        Map<String, Object> map = collectionService.getMyCollection(userId);
        Map<String, Object> status = new HashMap<>();
        if (map != null) {
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
}
