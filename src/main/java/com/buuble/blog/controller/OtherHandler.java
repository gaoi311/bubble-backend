package com.buuble.blog.controller;

import com.buuble.blog.service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OtherHandler {
    @Autowired
    OtherService otherService;

    //获取最常用的15个标签
    @GetMapping("/mosttags")
    public Map<String,Object> getHotTags(){
        Map<String,Object> map = otherService.getHotTags();
        Map<String,Object> status = new HashMap<>();
        if (map!=null){
            status.put("code",200);
            status.put("msg","获取成功");
            map.put("status",status);
            return map;
        }else {
            status.put("code",404);
            status.put("msg","获取失败");
            Map<String,Object> map1 = new HashMap<>();
            map1.put("status",status);
            return map1;
        }
    }

    //获取推荐作者列表
    @GetMapping("/recommendedusers")
    public Map<String,Object> getHotAuthor(){
        Map<String,Object> map = otherService.getHotAuthor();
        Map<String,Object> status = new HashMap<>();
        if (map!=null){
            status.put("code",200);
            status.put("msg","获取成功");
            map.put("status",status);
            return map;
        }else {
            status.put("code",404);
            status.put("msg","获取失败");
            Map<String,Object> map1 = new HashMap<>();
            map1.put("status",status);
            return map1;
        }
    }
    //搜索关键字
    @GetMapping("/searchany")
    public Map<String,Object> search(@RequestParam("key") String keyword){  // 此处改动
        Map<String,Object> map =otherService.search(keyword);
        Map<String,Object> status = new HashMap<>();
        if (map!=null){
            status.put("code",200);
            status.put("msg","获取成功");
            map.put("status",status);
            return map;
        }else {
            status.put("code",404);
            status.put("msg","获取失败");
            Map<String,Object> map1 = new HashMap<>();
            map1.put("status",status);
            return map1;
        }
    }
}
