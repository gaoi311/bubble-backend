package com.buuble.blog.controller;

import com.buuble.blog.entity.Blogtag;
import com.buuble.blog.service.BlogtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BlogtagHandler {
    @Autowired
    BlogtagService blogtagService;

    @GetMapping("/blogsoftag/{tagId}")
    public Map<String, Object> getBlogsByTag(@PathVariable("tagId") Integer tagId) {
        Map<String, Object> map = blogtagService.getBlogsByTag(tagId);
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
