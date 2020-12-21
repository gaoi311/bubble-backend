package com.buuble.blog.controller;

import com.buuble.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TagHandler {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")  //获取所有标签
    public Map<String, Object> getAllTags() {
        Map<String, Object> map = tagService.findAllTags();
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
